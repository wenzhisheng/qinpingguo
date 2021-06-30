package com.schedule.task;

import com.alibaba.fastjson.JSONObject;
import com.common.appleaccountinfo.service.IAppleAccountInfoService;
import com.common.constant.CommonConst;
import com.common.model.AppleAccountInfoVO;
import com.common.util.CurlUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.text.MessageFormat;
import java.util.List;

@Component
public class CheckAccount {

    public static final Logger logger = LogManager.getLogger(CheckAccount.class);

    @Autowired
    private IAppleAccountInfoService iAppleAccountInfoService;
    //设备udid号
    private final String DEVICE_NUMBERS = "d038c6f9df2b086b31811af266cc5d9b0e016da4";
    //添加设备的设备名称
    private final String DEVICE_NAME = "0000";

    /**
     * @Author XIAOEN
     * @Description 定时刷新校验账号信息
     * @Date 2018/11/5 20:18
     * @Param []
     * @return void
     **/
    @Scheduled(cron = "0 0/2 * * * ?")
    public void configureTasks() throws Exception{
        //查询出所有开发者账号，进去保持会话信息
        AppleAccountInfoVO appleAccountInfoVO = new AppleAccountInfoVO();
        //是否会话 0:过期 1:正常
        appleAccountInfoVO.setIsSession(1);
        //获取所有会话正常的账号会话
        List<AppleAccountInfoVO> voList = iAppleAccountInfoService.listAppleAccountInfo(appleAccountInfoVO);
        for (AppleAccountInfoVO vo: voList){
            //获取所有账号定时刷新接口保持会话信息
            String result = CurlUtils.addAccount(vo.getCookieValue(), vo.getCsrf(), vo.getCsrfTs(), vo.getTeamId(), DEVICE_NUMBERS, DEVICE_NAME, vo.getDsessionId());
            //如果当前开发者账号会话过期，则重新取一个
            checkRequest200(vo, result);
            //判断421状态处理
            checkRequest421(result, vo.getTeamId() + "定时校验账号添加：", vo.getTeamId() + "定时校验账号添加状态：", vo.getCookieValue(), vo.getCsrf(), vo.getCsrfTs(), vo.getTeamId(), vo.getDsessionId());
        }
    }

    /**
     * 调用CRUL命令监听苹果校验账号接口
     **/
    public String checkAccount(String cookie, String csrf, String csrf_ts, String teamId,String DSESSIONIDEND) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{4}\" -d \"includeInactiveProfiles=true&onlyCountLists=true&sidx=name&sort=name%3dasc&teamId={3}&type=limited&pageNumber=1&pageSize=500\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/listProvisioningProfiles.action?teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,DSESSIONIDEND);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("appleCheckAccount curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("appleCheckAccount result is {}",CurlUtils.controlLogInput(result));
            p.waitFor();
            p.destroy();
            return result;
        }finally {
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 检查请求200
     * @Date 2018/11/27 23:32
     * @Param [vo, result]
     * @return void
     **/
    public void checkRequest200(AppleAccountInfoVO vo, String result){
        String resultStatus = result.substring(9, 12);
        //200表示正常请求
        if (CommonConst.APPLE_RESULT_STATUS_200.equals(resultStatus)) {
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            JSONObject jsonObject = JSONObject.parseObject(result);
            //正常请求返回状态码
            String resultCode = jsonObject.get(CommonConst.JSON_KEY_RESULTCODE).toString();
            //1100表示会话过期
            if (CommonConst.APPLE_RESULT_STATUS_1100.equals(resultCode)) {
                AppleAccountInfoVO appleAccountInfoVO1 = new AppleAccountInfoVO();
                appleAccountInfoVO1.setTeamId(vo.getTeamId());
                //是否会话 0:过期 1:正常
                appleAccountInfoVO1.setIsSession(0);
                iAppleAccountInfoService.updateAppleAccountInfoIsSession(appleAccountInfoVO1);
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 定时任务检查421
     * @Date 2018/11/27 23:52
     * @Param [result, describe, describe1, cookie, csrf, csrf_ts, teamId, dsessionId, DEVICE_NUMBERS]
     * @return void
     **/
    public void checkRequest421(String result, String describe, String describe1,
                                String cookie, String csrf, String csrf_ts, String teamId,
                                String dsessionId) throws Exception{
        AppleAccountInfoVO appleAccountInfo = new AppleAccountInfoVO();
        appleAccountInfo.setTeamId(teamId);
        String resultStatus = result.substring(9, 12);
        AppleAccountInfoVO appleAccountInfoVO = new AppleAccountInfoVO();
        if (CommonConst.APPLE_RESULT_STATUS_421.equals(resultStatus)){
            if(result.contains(CommonConst.APPLE_RESULT_BAD_REQUEST)){
                int csrfIndex = result.indexOf(CommonConst.INTERCEPTING_CSRF);
                int csrf_tsIndex = result.indexOf(CommonConst.INTERCEPTING_CSRF_TS);
                //只有这个改变了请求头才有这个信息
                if (result.contains(CommonConst.INTERCEPTING_DSESSIONID)){
                    int DSESSIONIDIndex = result.indexOf(CommonConst.INTERCEPTING_SET_COOKIE);
                    int DSESSIONIDEnd = result.indexOf(CommonConst.INTERCEPTING_PATH);
                    //获取最新DSESSIONID
                    appleAccountInfo.setDsessionId(result.substring(DSESSIONIDIndex+12,DSESSIONIDEnd));
                }
                //出现421重新更新账号会话信息
                //获取最新csrf
                appleAccountInfo.setCsrf(result.substring(csrfIndex+6, csrfIndex+70));
                //获取最新csrf_ts
                appleAccountInfo.setCsrfTs(result.substring(csrf_tsIndex+9,csrf_tsIndex+22));
                iAppleAccountInfoService.updateAppleAccountInfo421(appleAccountInfo);
                return;
            }
            logger.info(describe+resultStatus);
            result = checkAccount(cookie, csrf, csrf_ts, teamId, dsessionId);
            int csrfIndex = result.indexOf(CommonConst.INTERCEPTING_CSRF);
            int csrf_tsIndex = result.indexOf(CommonConst.INTERCEPTING_CSRF_TS);
            //只有这个改变了请求头才有这个信息
            if (result.contains(CommonConst.INTERCEPTING_DSESSIONID)){
                int DSESSIONIDIndex = result.indexOf(CommonConst.INTERCEPTING_SET_COOKIE);
                int DSESSIONIDEnd = result.indexOf(CommonConst.INTERCEPTING_PATH);
                //获取最新DSESSIONID
                appleAccountInfo.setDsessionId(result.substring(DSESSIONIDIndex+12,DSESSIONIDEnd));
            }
            //出现421重新更新账号会话信息
            //获取最新csrf
            appleAccountInfo.setCsrf(result.substring(csrfIndex+6, csrfIndex+70));
            //获取最新csrf_ts
            appleAccountInfo.setCsrfTs(result.substring(csrf_tsIndex+9,csrf_tsIndex+22));
            iAppleAccountInfoService.updateAppleAccountInfo421(appleAccountInfo);
        }else{
            if (result.contains(CommonConst.BRACE_LEFT)){
                int start = result.indexOf(CommonConst.BRACE_LEFT);
                result = result.substring(start);
                JSONObject jsonObject = JSONObject.parseObject(result);
                //正常请求返回状态码
                String resultCode = jsonObject.get(CommonConst.JSON_KEY_RESULTCODE).toString();
                if (CommonConst.APPLE_RESULT_STATUS_0.equals(resultCode)){
                    logger.info(describe1+resultCode);
                }else if (CommonConst.APPLE_RESULT_STATUS_1100.equals(resultCode)){
                    //接口返回1100状态表示会话cookie过期，便更新会话状态为过期，后台重新录入
                    appleAccountInfoVO.setTeamId(teamId);
                    //是否会话 0:过期 1:正常
                    appleAccountInfoVO.setIsSession(0);
                    iAppleAccountInfoService.updateAppleAccountInfoIsSession(appleAccountInfoVO);
                    logger.info(teamId + "会话过期请重新录入会话：" + CurlUtils.controlLogInput(result));
                    return;
                }else{
                    logger.error(describe+"失败："+result);
                    return;
                }
            }else{
                logger.info("错误请求(Bad Request)");
            }
        }
    }

}
