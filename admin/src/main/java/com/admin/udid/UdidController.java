package com.admin.udid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.appleaccountinfo.service.IAppleAccountInfoService;
import com.common.appleappidid.service.IAppleAppididService;
import com.common.appleudid.service.IAppleUdidService;
import com.common.constant.CommonConst;
import com.common.exception.DescribeException;
import com.common.model.*;
import com.common.util.CommonUtil;
import com.common.util.CurlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/apple")
@Api(value = "Udid Controller", tags = "Udid Controller", description = "设备信任流程")
public class UdidController {

    private static final Logger logger = LogManager.getLogger(UdidController.class);

    //下载根路径，测试路径(Resign-ipa/)，生产路径(8bet/)
    private static final String SHELL_PATHS = "https://appxz1.com/Resign-ipa/";
    //配置文件
    private static final String UDID_PATHS = "https://appxz1.com/admin/static/udid.mobileconfig";
    //等待信任设备
    private static final String TRUST_WAIT_PATHS = "https://appxz1.com/admin/static/dependence.html";
    //指定的描述文件名称(downLoadApp)
    private static final String PROVISIONING_PROFILESNAME = "downLoadAPP";
    //设备名称
    private static final String DEVICE_NAME = "0000";
    //会话时间
    private static Map<String, Long> UDIDS = new HashMap<String, Long>();
    //保存代理值，以IP为键
    public static final Map<String, String> localCache = new ConcurrentHashMap<String, String>();
    //同步锁
    private Lock rLock = new ReentrantLock();

    @Autowired
    private IAppleAccountInfoService iAppleAccountInfoService;
    @Autowired
    private IAppleUdidService iAppleUdidService;
    @Autowired
    private IAppleAppididService iAppleAppididService;

    /**
     * @Author XIAOEN
     * @Description 获取当前访问IP及代理值
     * @Date 2018/12/3 16:35
     * @Param [request, response]
     * @return void
     **/
    @GetMapping("/getVisitIP")
    @ApiOperation(value = "获取访问IP", notes = "获取访问IP")
    public void getVisitIP(HttpServletRequest request, HttpServletResponse response)throws Exception {
        //使用同步锁，防止并发
        rLock.lock();
        try{
            //当前用户代理操作IP
            String ipAddr = CommonUtil.getIpAddr(request);
            if(!CommonUtil.ipCheck(ipAddr)){
                throw new DescribeException("获取IP格式不正确", -1);
            }
            //用户代理值
            String proxyAccount = request.getParameter(CommonConst.PROXY_ACCOUNT);
            //代理号为空字符("")或者(null)或者null字符(null)或者字符(undefined)，则为default
            if (CommonConst.NUL_NULL_CHARACTER.equals(proxyAccount) ||
                    CommonConst.UNDEFINED_CHARACTER.equals(proxyAccount) ||
                    proxyAccount == null || CommonConst.NULL_CHARACTER.equals(proxyAccount)){
                proxyAccount = CommonConst.PROXY_DEFAULT;
            }
            logger.info("获取访问IP："+ipAddr+"————"+proxyAccount);
            //写入map中
            localCache.put(ipAddr,proxyAccount);
            //重定向到配置文件获取udid
            response.sendRedirect(UDID_PATHS);
        }finally {
            rLock.unlock();
        }
    }

    /**
     * @Author XIAOEN
     * @Description 获取设备号UDID
     * @Date 2018/12/20 16:05
     * @Param [request, response]
     * @return void
     **/
    @PostMapping("/udid")
    @ApiOperation(value = "获取设备udid", notes = "获取设备udid")
    public void findUdid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //获取ip对比拿取代理值传给app
        String ipAddr = CommonUtil.getIpAddr(request);
        if (!CommonUtil.ipCheck(ipAddr)) {
            throw new DescribeException("获取IP格式不正确", -1);
        }
        String proxyAccount = localCache.get(ipAddr);
        //清空map代理键值
        localCache.clear();
        logger.info("实际对比IP：" + ipAddr + "————" + proxyAccount);
        //301之后iOS设备会自动打开safari浏览器
        response.setStatus(301);
        //获取udid
        String deviceNumbers = getUdid(request);
        logger.info("获取到设备udid：" + deviceNumbers);
        //https://appxz9.com/admin/static/dependence.html?deviceNumbers=111&proxyAccount=222
        response.setHeader(CommonConst.LOCATION, TRUST_WAIT_PATHS+"?deviceNumbers="+deviceNumbers+"&proxyAccount="+proxyAccount);
    }

    /**
     * @Author XIAOEN
     * @Description 信任下载
     * @Date 2018/12/19 21:52
     * @Param [request, response]
     * @return void
     **/
    @PostMapping("/dependence")
    @ApiOperation(value = "信任下载", notes = "信任下载")
    public String dependenceApple(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //用户代理编码
        String proxyAccount = request.getParameter("proxyAccount");
        //用户苹果设备号
        String deviceNumbers = request.getParameter("deviceNumbers");
        //控制单个用户一分钟之内只能访问一次
        visitControl(UDIDS, deviceNumbers);
        //查询出更新时间最旧的一个开发者账号
        AppleAccountInfoVO appleAccountInfo = new AppleAccountInfoVO();
        //未满100
        appleAccountInfo.setIsEnable(1);
        //会话正常
        appleAccountInfo.setIsSession(1);
        //取一个更新时间最旧的开发者账号
        appleAccountInfo = iAppleAccountInfoService.selectAppleAccountInfo(appleAccountInfo);
        if (appleAccountInfo == null) {
            throw new DescribeException("找不到可用开发者账号", -1);
        }
        //更新时间以便下次获取
        iAppleAccountInfoService.updateAppleAccountInfo(appleAccountInfo);
        //苹果开发者账号cookie
        String cookie = appleAccountInfo.getCookieValue();
        //苹果开发者账号csrf
        String csrf = appleAccountInfo.getCsrf();
        //苹果开发者账号csrf_ts
        String csrf_ts = appleAccountInfo.getCsrfTs();
        //苹果开发者账号teamId
        String teamId = appleAccountInfo.getTeamId();
        //苹果开发者账号dsessionId
        String dsessionId = appleAccountInfo.getDsessionId();
        logger.info("检查设备号是否添加：" + deviceNumbers);
        //获取当前开发者账号的所有设备号，判断开发者账号中是否有添加过当前设备，或者账号已添加满100个设备
        AppleUdidVO appleUdid = new AppleUdidVO();
        appleUdid.setDeviceNumbers(deviceNumbers);
        List<AppleUdidVO> listAppleUdid = iAppleUdidService.listAppleUdid(appleUdid);
        logger.info("udid是否存在：" + listAppleUdid.size());
        if (listAppleUdid.size() != CommonConst.NUMBER_0) {
            //直接获取下载应用链接下载
            getDownloadApply(listAppleUdid, proxyAccount, deviceNumbers);
        } else {
            //设备满100，重新获取开发者账号
            appleAccountInfo = againAppleAccountInfo(appleAccountInfo, deviceNumbers);
            //添加设备号到苹果
            addDeviceNumbersToApple(appleAccountInfo, teamId, cookie, csrf, csrf_ts, deviceNumbers, dsessionId);
        }
        //获取描述文件信息
        ProvisioningProfilesVO ppVO = getProvisioningProfilesVO(appleAccountInfo, cookie, csrf, csrf_ts, dsessionId);
        //重置描述文件
        resetProvisioningProfiles(ppVO, appleAccountInfo, cookie, csrf, csrf_ts, dsessionId);
        //获取开发者账号配置路径
        StringBuilder filePaths = new StringBuilder();
        String filePath = appleAccountInfo.getFilePath();
        filePaths.append(filePath);
        filePaths.append(ppVO.getIdentifier());
        //路径，数据库配置路径末尾要加斜杠
        filePaths.append(CommonConst.LEFT_SLASH);
        //下载描述文件
        CurlUtils.downloadDescribeFile(cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), filePaths.toString(), ppVO.getProvisioningProfileId(), dsessionId);
        logger.info("描述文件下载成功：" + ppVO.getCertificateId());
        //执行shell脚本
        execShell(appleAccountInfo.getShellPath(), proxyAccount);
        //创建plist文件
        String path = createPlist(filePath, ppVO.getIdentifier());
        //创建下载页面
        String htmlPath = createHtml(filePath, path, ppVO.getIdentifier());
        //保存数据
        insertDate(appleAccountInfo, ppVO.getIdentifier(), filePaths.toString(), deviceNumbers);
        //返回应用下载页面
        logger.info("信任流程结束：" + deviceNumbers);
        return htmlPath;
    }

    /**
     * @return void
     * @Author XIAOEN
     * @Description 执行shell脚本
     * @Date 2018/11/5 17:41
     * @Param []
     **/
    public static void execShell(String shellPaths, String proxyAccount) throws Exception {
        //String shellPath = "chmod 755 " + shellPaths;//获取执行权限
        String shellPath2;
        shellPath2 = "sh " + shellPaths + " '" + proxyAccount + "'";
        //logger.info("Shell执行授权命令："+shellPath);
        logger.info("Shell执行签名命令：" + shellPath2);
        Runtime runtime = Runtime.getRuntime();
        //runtime.exec(shellPath);
        Process pro = runtime.exec(shellPath2);
        int status = pro.waitFor();
        if (status != CommonConst.NUMBER_0) {
            logger.info("Shell脚本执行失败：" + status);
            throw new DescribeException("Shell脚本执行失败", -1);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        StringBuffer strbr = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            strbr.append(line).append("\n");
        }
        String result = strbr.toString();
        logger.info("Shell脚本执行结束：" + result);
    }

    /**
     * @return void
     * @Author XIAOEN
     * @Description 检查执行请求
     * @Date 2018/11/6 22:34
     * @Param [result]
     **/
    public TempSessionVO checkRequest421(String result1, String describe, String cookie, String csrf,
                                         String csrf_ts, String teamId, String dsessionId) throws Exception {
        TempSessionVO tempSessionVO = new TempSessionVO();
        AppleAccountInfoVO appleAccountInfo = new AppleAccountInfoVO();
        appleAccountInfo.setTeamId(teamId);
        String result = result1.substring(9, 12);
        //更新csrf:与csrf_ts:的值
        if (CommonConst.APPLE_RESULT_STATUS_421.equals(result)) {
            result = CurlUtils.checkAccount(cookie, csrf, csrf_ts, teamId, dsessionId);
            int csrfIndex = result.indexOf(CommonConst.INTERCEPTING_CSRF);
            int csrf_tsIndex = result.indexOf(CommonConst.INTERCEPTING_CSRF_TS);
            int DSESSIONIDIndex = result.indexOf(CommonConst.INTERCEPTING_SET_COOKIE);
            int DSESSIONIDEnd = result.indexOf(CommonConst.INTERCEPTING_PATH);
            //获取最新csrf
            csrf = result.substring(csrfIndex + 6, csrfIndex + 70);
            //获取最新csrf_ts
            csrf_ts = result.substring(csrf_tsIndex + 9, csrf_tsIndex + 22);
            //获取最新DSESSIONID
            String DSESSIONID = result.substring(DSESSIONIDIndex + 12, DSESSIONIDEnd);
            //出现421重新更新账号会话信息
            appleAccountInfo.setCsrf(csrf);
            appleAccountInfo.setCsrfTs(csrf_ts);
            appleAccountInfo.setDsessionId(DSESSIONID);
            iAppleAccountInfoService.updateAppleAccountInfo421(appleAccountInfo);
            tempSessionVO.setCsrf(csrf);
            tempSessionVO.setCsrfTs(csrf_ts);
            tempSessionVO.setDsessionId(DSESSIONID);
            //是否421：0:不是 1:是
            tempSessionVO.setIs421(1);
            return tempSessionVO;
        } else {
            //是否成功
            isSuccess(result1, describe, teamId);
            //是否421：0:不是 1:是
            tempSessionVO.setIs421(0);
            return tempSessionVO;
        }
    }

    //生成plist文件和HTML
    public static String createPlist(String filePaths, String identifier) {
        //https://appxz9.com/8bet/com.freeCertificate.number1/com.freeCertificate.number1.ipa
        logger.info("==========开始创建plist文件");
        String plistFile = CommonConst.FILE_NAME_PLIST;
        final String path = filePaths + identifier;
        String pathResult = SHELL_PATHS + identifier + plistFile;
        final String PLIST_PATH = path + plistFile;
        File file = new File(PLIST_PATH);
        File file1 = new File(path);
        try {
            if (!file1.isDirectory()) {
                file1.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            logger.info("==========创建plist文件异常：" + e);
        }
        String bundleID = "<string>" + identifier + "</string>\n";
        String urlIPA = "<string>" + SHELL_PATHS + identifier + "/" + identifier + ".ipa" + "</string>\n";
        String plist = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
                + "<plist version=\"1.0\">\n" + "<dict>\n"
                + "<key>items</key>\n"
                + "<array>\n"
                + "<dict>\n"
                + "<key>assets</key>\n"
                + "<array>\n"
                + "<dict>\n"
                + "<key>kind</key>\n"
                + "<string>software-package</string>\n"
                + "<key>url</key>\n"
                //ipa文件路径
                + urlIPA
                + "</dict>\n"
                + "<dict>\n"
                + "<key>kind</key>\n"
                + "<string>full-size-image</string>\n"
                + "<key>url</key>\n"
                + "<string>https://appxz1.com/8bet/512.png</string>\n"
                + "</dict>\n"
                + "<dict>\n"
                + "<key>kind</key>\n"
                + "<string>display-image</string>\n"
                + "<key>url</key>\n"
                + "<string>https://appxz1.com/8bet/512.png</string>\n"
                + "</dict>\n"
                + "</array>\n"
                + "<key>metadata</key>\n"
                + "<dict>\n"
                + "<key>bundle-identifier</key>\n"
                //这个是开发者账号用户名，也可以为空，为空安装时看不到图标，完成之后可以看到
                + bundleID
                + "<key>bundle-version</key>\n"
                + "<string>1.0</string>\n"
                + "<key>kind</key>\n"
                + "<string>software</string>\n"
                + "<key>title</key>\n"
                + "<string>8BET棋牌</string>\n"
                + "</dict>\n"
                + "</dict>\n"
                + "</array>\n"
                + "</dict>\n"
                + "</plist>";
        try {
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(output, "UTF-8");
            writer.write(plist);
            writer.close();
            output.close();
        } catch (Exception e) {
            logger.info("==========创建plist文件异常：" + e);
        }
        logger.info("==========成功创建plist文件");
        return pathResult;
    }

    //创建HTML
    public static String createHtml(String filePaths, String plistPath, String identifier) {
        //'https://appxz9.com/8bet/com.freeCertificate.number1/a.plist';
        logger.info("==========开始创建html文件");
        String path = filePaths + identifier;
        String pathResult = SHELL_PATHS + identifier + "/downLoad.html";
        logger.info("downLoad:" + pathResult);
        String HTML_PATH = path + "/downLoad.html";
        File file = new File(HTML_PATH);
        File file1 = new File(path);
        try {
            if (!file1.isDirectory()) {
                file1.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            logger.error("==========创建html文件异常", e);
        }
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"utf-8\">\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no\">\n" +
                "\t\t<script type=\"text/javascript\" src=\"https://appxz1.com/admin/static/js/flexible.js\"></script>\n" +
                "\t\t<link rel=\"stylesheet\" href=\"https://appxz1.com/admin/static/css/style.css\" />\n" +
                "\t\t<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">\n" +
                "\t\t<title>8BET棋牌安装</title>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<section class=\"load\">\n" +
                "\t\t\t<section class=\"bar\">\n" +
                "\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t<img src=\"https://appxz1.com/admin/static/img/info.png\" />\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"pro\">\n" +
                "\t\t\t\t\t<div class=\"progress-bar pro-bar-info progress-bar-striped actived\" style=\"width: 100%;\">\n" +
                "\t\t\t\t\t\t<div class=\"progress-value\">100%</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</section>\n" +
                "\t\t\t<!--弹窗-->\n" +
                "\t\t\t<section class=\"mask\" id=\"info\">\n" +
                "\t\t\t\t<img src=\"https://appxz1.com/admin/static/img/a.png\" />\n" +
                "\t\t\t</section>\n" +
                "\t\t</section>\n" +
                "\t\t<script>\n" +
                "\t\t\tsetTimeout(\n" +
                "\t\t\t\tfunction() {\n" +
                "\t\t\t\t\tvar elem = document.getElementById('info');\n" +
                "\t\t\t\t\telem.style.display='block';\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t5000)\n" +
                "\t\t</script>\n" +
                "\t\t</script>\n" +
                "\t\t<script type=\"text/javascript\">\n" +
                "\t\tvar url = '" + plistPath + "';\n" +
                "\t\twindow.location.href = \"itms-services://?action=download-manifest&url=\" + url;\n" +
                "\t\t</script>\n" +
                "\t</body>\n" +
                "</html>";
        try {
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
            writer.write(html);
            writer.close();
            output.close();
        } catch (IOException e) {
            logger.error("==========创建html文件异常", e);
        }
        logger.info("==========成功创建html文件");
        return pathResult;
    }

    /**
     * @return int
     * @Author XIAOEN
     * @Description 获取JSONArray数组索引
     * @Date 2018/11/15 18:53
     * @Param [objects, tempName]
     **/
    public int getJSONArrayIndex(JSONArray objects, String tempName) {
        //临时数组索引
        int tempIndex = -1;
        String name;
        //根据指定名称对比获取数组索引
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) objects.get(i);
            if (PROVISIONING_PROFILESNAME.equals(tempName)) {
                //描述文件名称
                name = (String) jsonObject1.get(CommonConst.JSON_KEY_NAME);
            } else {
                //证书标识符
                name = (String) jsonObject1.get(CommonConst.JSON_KEY_IDENTIFIER);
            }
            if (tempName.equals(name)) {
                tempIndex = i;
                break;
            }
        }
        if (-1 == tempIndex) {
            throw new DescribeException("获取信息失败" + objects.toString(), -1);
        }
        return tempIndex;
    }

    /**
     * @return void
     * @Author XIAOEN
     * @Description 是否添加成功
     * @Date 2018/11/7 14:16
     * @Param [result]
     **/
    public void isSuccess(String result, String describe, String teamId) {
        if (result.contains(CommonConst.BRACE_LEFT)) {
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer resultCode = (Integer) jsonObject.get(CommonConst.JSON_KEY_RESULTCODE);
            //返回状态0代表成功
            if (0 == resultCode) {
                logger.info(describe + "成功：" + result);
            } else if (1100 == resultCode) {
                //接口返回1100状态表示会话cookie过期，便更新会话状态为过期，后台重新录入
                AppleAccountInfoVO appleAccountInfoVO4 = new AppleAccountInfoVO();
                appleAccountInfoVO4.setTeamId(teamId);
                appleAccountInfoVO4.setIsSession(0);//是否会话 0:过期 1:正常
                iAppleAccountInfoService.updateAppleAccountInfoIsSession(appleAccountInfoVO4);
                throw new DescribeException(teamId + "会话过期请重新录入会话：" + result, -1);
            } else {
                throw new DescribeException(describe + "失败：" + result, -1);
            }
        } else {
            logger.info("错误请求(Bad Request)");
            throw new DescribeException("Bad Request", -1);
        }
    }

    /**
     * @return java.lang.String
     * @Author XIAOEN
     * @Description 重置描述文件之后重置描述文件Id
     * @Date 2018/11/16 13:30
     * @Param [result]
     **/
    public String resetProvisioningProfileId(String result) {
        int startIndex = result.indexOf(CommonConst.BRACE_LEFT);
        result = result.substring(startIndex);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject jsonObject1 = (JSONObject) jsonObject.get(CommonConst.JSON_KEY_PROVISIONINGPROFILE);
        return (String) jsonObject1.get(CommonConst.JSON_KEY_PROVISIONINGPROFILEID);
    }

    /**
     * @return java.lang.String
     * @Author XIAOEN
     * @Description 调用CRUL命令获取设备信息
     * @Date 2018/11/4 21:46
     * @Param [cookie, csrf, csrf_ts, teamId]
     **/
    public String getListProvisioningProfiles(String cookie, String csrf, String csrf_ts, String teamId, String dsessionId) throws Exception {
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{4}\" -d \"includeInactiveProfiles=true&onlyCountLists=true&sidx=name&sort=name%3dasc&teamId={3}&type=limited&pageNumber=1&pageSize=500&name=CosmicBlast\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/listProvisioningProfiles.action?teamId={3}&name=CosmicBlast\"";
        String str = MessageFormat.format(url, cookie, csrf, csrf_ts, teamId, dsessionId);
        ProcessBuilder pb = new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("appleProvisioningProfiles curl is {}", str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream = null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream, "utf-8");
            logger.info("appleProvisioningProfiles result is {}", CurlUtils.controlLogInput(result));
            p.waitFor();
            p.destroy();
            CurlUtils.checkRequest(result, "登录信息失效", "列表获取设备信息失败：");
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String resultCode = jsonObject.get(CommonConst.JSON_KEY_RESULTCODE).toString();
            if (CommonConst.APPLE_RESULT_STATUS_1100.equals(resultCode)) {
                //接口返回1100状态表示会话cookie过期，便更新会话状态为过期，后台重新录入
                AppleAccountInfoVO appleAccountInfoVO5 = new AppleAccountInfoVO();
                appleAccountInfoVO5.setTeamId(teamId);
                appleAccountInfoVO5.setIsSession(0);//是否会话 0:过期 1:正常
                iAppleAccountInfoService.updateAppleAccountInfoIsSession(appleAccountInfoVO5);
                throw new DescribeException(teamId + "会话过期请重新录入会话：" + result, -1);
            } else {
                result = jsonObject.get(CommonConst.JSON_KEY_PROVISIONINGPROFILES).toString();
                return result;
            }
        } finally {
            if (null != bufferedInputStream) {
                bufferedInputStream.close();
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 读取流获取设备信息
     * @Date 2018/11/28 0:44
     * @Param [request]
     * @return java.lang.String
     **/
    public String getUdid(HttpServletRequest request){
        Map<String, String> dictMap = new HashMap<String, String>();
        try {
            //获取HTTP请求的输入流
            InputStream is = request.getInputStream();
            //已HTTP请求输入流建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            //读取HTTP请求内容
            String buffer;
            while ((buffer = br.readLine()) != null) {
                sb.append(buffer);
            }
            //content就是接收到的xml字符串
            String content = sb.toString().substring(sb.toString().indexOf("<?xml"), sb.toString().indexOf("</plist>") + 8);
            logger.info("Apple result xml：" + content);
            //进行xml解析即可
            Document document = DocumentHelper.parseText(content);
            Element rootElement = document.getRootElement();//获取根节点
            for (Iterator<?> iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();//遍历获取下一个节点元素
                for (Iterator<?> iterator2 = element.elementIterator(); iterator2.hasNext(); ) {
                    Element element2 = (Element) iterator2.next();
                    //只获取UDID元素值，XS MAX长度25，其它长度40
                    if (40 == element2.getTextTrim().length() || 25 == element2.getTextTrim().length()) {
                        //将子节点的名称和值分别插入map中
                        dictMap.put(element2.getName(), element2.getTextTrim());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Xml parse error：：" + e);
            return null;
        }
        //获取udid
        String deviceNumbers = dictMap.get("string");
        if ("".equals(deviceNumbers) || deviceNumbers==null){
            throw new DescribeException("获取设备号失败", -1);
        }
        return deviceNumbers;
    }

    /**
     * @Author XIAOEN
     * @Description 控制单个用户一分钟之内只能访问一次
     * @Date 2018/11/28 0:52
     * @Param [UDIDS, deviceNumbers]
     * @return void
     **/
    public void visitControl(Map<String, Long> UDIDS, String deviceNumbers){
        Long lastTime = UDIDS.get(deviceNumbers);
        if (null == lastTime) {
            UDIDS.put(deviceNumbers, System.currentTimeMillis());
        } else {
            Long xTime = System.currentTimeMillis() - lastTime;
            if (xTime < CommonConst.ACCESS_TIME_LIMIT) {
                throw new DescribeException("访问低于1分钟："+xTime, -1);
            }
            UDIDS.put(deviceNumbers, System.currentTimeMillis());
        }
    }

    /**
     * @Author XIAOEN
     * @Description 直接获取下载应用链接下载
     * @Date 2018/12/24 13:23
     * @Param [listAppleUdid, proxyAccount]
     * @return java.lang.String
     **/
    public String getDownloadApply(List<AppleUdidVO> listAppleUdid, String proxyAccount, String deviceNumbers) throws Exception{
        logger.info("设备号已添加：" + deviceNumbers);
        //重新获取一个启用的开发者账号
        AppleAccountInfoVO appleAccountInfoVO = new AppleAccountInfoVO();
        appleAccountInfoVO.setTeamId(listAppleUdid.get(0).getTeamId());
        AppleAccountInfoVO appleAccountInfo = iAppleAccountInfoService.selectAppleAccountInfo(appleAccountInfoVO);
        //执行shell脚本
        execShell(appleAccountInfo.getShellPath(), proxyAccount);
        //获取应用信息
        AppleAppididVO appleAppididVO = new AppleAppididVO();
        appleAppididVO.setTeamId(listAppleUdid.get(0).getTeamId());
        List<AppleAppididVO> list = iAppleAppididService.listAppleAppidid(appleAppididVO);
        //获取不到下载信息抛出异常
        if (list == null || list.size() == 0) {
            logger.error("teamId: " + appleAppididVO.getTeamId() + " 获取不到应用信息");
            throw new DescribeException("应用信息获取失败", -1);
        }
        //更新时间以便下次获取
        iAppleAccountInfoService.updateAppleAccountInfo(appleAccountInfo);
        AppleAppididVO appleAppididVO1 = list.get(0);
        return appleAppididVO1.getAppleididPath();
    }

    /**
     * @Author XIAOEN
     * @Description 设备满100，重新获取开发者账号
     * @Date 2018/12/24 14:39
     * @Param [appleAccountInfo, deviceNumbers]
     * @return com.common.model.AppleAccountInfoVO
     **/
    public AppleAccountInfoVO againAppleAccountInfo(AppleAccountInfoVO appleAccountInfo, String deviceNumbers){
        //如果获取账号添加设备满100，就重新获取启用、会话正常、未满100的账号
        if (appleAccountInfo.getDeviceLength() == CommonConst.DEVICE_LENGTH_100){
            AppleAccountInfoVO appleAccountInfoVO1 = new AppleAccountInfoVO();
            //未满100
            appleAccountInfoVO1.setIsEnable(1);
            //会话正常
            appleAccountInfoVO1.setIsSession(1);
            //设置设备长度等于101则查询小于这个数量的账号
            appleAccountInfoVO1.setDeviceLength(101);
            appleAccountInfo = iAppleAccountInfoService.selectAppleAccountInfo(appleAccountInfoVO1);
            if(appleAccountInfo == null){
                throw new DescribeException("找不到可用开发者账号：" + deviceNumbers, -1);
            }
            return appleAccountInfo;
        }
        return null;
    }

    /**
     * @Author XIAOEN
     * @Description 判断是否会话过期处理
     * @Date 2018/12/24 15:29
     * @Param [resultCode, cookie, csrf, csrf_ts, deviceNumbers, dsessionId]
     * @return java.lang.String
     **/
    public String sessionExpiration(String resultCode, String cookie, String csrf, String csrf_ts, String deviceNumbers, String dsessionId) throws Exception{
        //1100表示会话过期
        if (CommonConst.APPLE_RESULT_STATUS_1100.equals(resultCode)) {
            AppleAccountInfoVO appleAccountInfoVO2 = new AppleAccountInfoVO();
            //未满100
            appleAccountInfoVO2.setIsEnable(1);
            //会话正常
            appleAccountInfoVO2.setIsSession(1);
            AppleAccountInfoVO appleAccountInfo = iAppleAccountInfoService.selectAppleAccountInfo(appleAccountInfoVO2);
            //重新赋值开发者合作号Id
            String teamId = appleAccountInfo.getTeamId();
            return CurlUtils.addAccount(cookie, csrf, csrf_ts, teamId, deviceNumbers, DEVICE_NAME, dsessionId);
        }
        return null;
    }

    /**
     * @Author XIAOEN
     * @Description 添加设备号到苹果
     * @Date 2018/12/24 19:40
     * @Param [appleAccountInfo, teamId, cookie, csrf, csrf_ts, deviceNumbers, dsessionId]
     * @return void
     **/
    public void addDeviceNumbersToApple(AppleAccountInfoVO appleAccountInfo, String teamId, String cookie, String csrf, String csrf_ts, String deviceNumbers, String dsessionId) throws Exception{
        logger.info("添加设备号："+appleAccountInfo.getTeamId());
        String result = CurlUtils.addAccount(cookie, csrf, csrf_ts, teamId, deviceNumbers, DEVICE_NAME, dsessionId);
        //如果当前开发者账号会话过期，则重新取一个
        String resultStatus = result.substring(9, 12);
        //200表示正常请求
        if (CommonConst.APPLE_RESULT_STATUS_200.equals(resultStatus)) {
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String resultCode = jsonObject.get(CommonConst.JSON_KEY_RESULTCODE).toString();
            //判断是否会话过期处理
            result = sessionExpiration(resultCode, cookie, csrf, csrf_ts, deviceNumbers, dsessionId);
        }
        //判断421状态处理
        TempSessionVO ts = checkRequest421(result, CommonConst.HINT_INSER_DEVICES, cookie, csrf, csrf_ts, teamId, dsessionId);
        if (CommonConst.NUMBER_1 == ts.getIs421()) {//表示是421重新获取到会话信息再请求
            result = CurlUtils.addAccount(cookie, ts.getCsrf(), ts.getCsrfTs(), teamId, deviceNumbers, DEVICE_NAME, ts.getDsessionId());
            //如果当前开发者账号会话过期，则重新取一个
            resultStatus = result.substring(9, 12);
            //200表示正常请求
            if (CommonConst.APPLE_RESULT_STATUS_200.equals(resultStatus)) {
                int start = result.indexOf(CommonConst.BRACE_LEFT);
                result = result.substring(start);
                JSONObject jsonObject = JSONObject.parseObject(result);
                String resultCode = jsonObject.get(CommonConst.JSON_KEY_RESULTCODE).toString();
                //判断是否会话过期处理
                result = sessionExpiration(resultCode, cookie, csrf, csrf_ts, deviceNumbers, dsessionId);
            }
            //是否成功
            isSuccess(result, "添加设备号", teamId);
        }
    }

    /**
     * @Author XIAOEN
     * @Description 获取描述文件信息
     * @Date 2018/12/24 19:06
     * @Param [appleAccountInfo, cookie, csrf, csrf_ts, dsessionId]
     * @return com.common.model.ProvisioningProfilesVO
     **/
    public ProvisioningProfilesVO getProvisioningProfilesVO(AppleAccountInfoVO appleAccountInfo, String cookie, String csrf, String csrf_ts, String dsessionId) throws Exception{
        String provisioningProfiles = getListProvisioningProfiles(cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), dsessionId);
        logger.info("获取描述文件信息：" + provisioningProfiles);
        ProvisioningProfilesVO ppVO = new ProvisioningProfilesVO();
        if (provisioningProfiles != null && !CommonConst.BRACKET_TWAIN.equals(provisioningProfiles)) {
            JSONArray objects = JSONObject.parseArray(provisioningProfiles);
            int tempIndex = getJSONArrayIndex(objects, PROVISIONING_PROFILESNAME);
            JSONObject jsonObject1 = (JSONObject) objects.get(tempIndex);
            //描述文件Id
            ppVO.setProvisioningProfileId((String) jsonObject1.get(CommonConst.JSON_KEY_PROVISIONINGPROFILEID));
            //描述文件名称
            ppVO.setName((String) jsonObject1.get(CommonConst.JSON_KEY_NAME));
            //描述文件请求Id(requestId)
            ppVO.setUUID((String) jsonObject1.get(CommonConst.JSON_KEY_UUID));
            //获取单个描述文件属性内容
            JSONObject provisioningProfile = CurlUtils.getProvisioningProfile(cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), ppVO.getProvisioningProfileId(), dsessionId);
            JSONArray certificatesJSONArray = (JSONArray) provisioningProfile.get(CommonConst.JSON_KEY_CERTIFICATES);
            JSONObject certificatesJSONObject = (JSONObject) certificatesJSONArray.get(0);
            //描述文件证书Id，certificateId
            ppVO.setCertificateId((String) certificatesJSONObject.get(CommonConst.JSON_KEY_CERTIFICATEID));
            //证书名称，name
            String certificatesName = (String) certificatesJSONObject.get("name");
            JSONObject appIdJSONObject = (JSONObject) provisioningProfile.get(CommonConst.JSON_KEY_APPID);
            //标识符，identifier
            ppVO.setIdentifier((String) appIdJSONObject.get(CommonConst.JSON_KEY_IDENTIFIER));
            //证书Id和名称 实例：iPhone Developer:  Hanyu Yang \(Z9BB9BC3UU\)
            ppVO.setCertificateIdAndName(certificatesName + " (" + "Z9BB9BC3UU" + ")");
            return ppVO;
        } else {
            throw new DescribeException("请先添加描述文件"+appleAccountInfo.getTeamId(), -1);
        }
    }

    /**
     * @Author XIAOEN
     * @Description 重置描述文件
     * @Date 2018/12/24 19:26
     * @Param [ppVO, appleAccountInfo, cookie, csrf, csrf_ts, dsessionId]
     * @return void
     **/
    public void resetProvisioningProfiles(ProvisioningProfilesVO ppVO, AppleAccountInfoVO appleAccountInfo, String cookie, String csrf, String csrf_ts, String dsessionId) throws Exception{
        logger.info("开始重置描述文件");
        //获取当前开发者账号的所有设备号Id
        List<String> listDevicesId = CurlUtils.getListDevicesId(cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), dsessionId);
        //把当前开发着账号设备数量记录到当前账号
        appleAccountInfo.setDeviceLength(listDevicesId.size());
        iAppleAccountInfoService.updateAppleAccountInfo(appleAccountInfo);
        //把所有设备拼接成url参数
        StringBuilder listDevice = new StringBuilder();
        if (listDevicesId != null && listDevicesId.size() != CommonConst.NUMBER_0) {
            for (String str : listDevicesId) {
                listDevice.append(CommonConst.DEVICEIDS + str);
            }
        }
        //获取appIdId列表
        String listAppIds = CurlUtils.getlistAppIds(cookie, csrf, appleAccountInfo.getTeamId(), csrf_ts);
        JSONObject jsonObject = JSONObject.parseObject(listAppIds);
        JSONArray devices = (JSONArray) jsonObject.get(CommonConst.JSON_KEY_APPIDS);
        //获取证书标识符数组索引
        int tempIndex = getJSONArrayIndex(devices, ppVO.getIdentifier());
        JSONObject jsonObject2 = (JSONObject) devices.get(tempIndex);
        //证书标识符
        String appIdId = (String) jsonObject2.get(CommonConst.JSON_KEY_APPIDID);
        //删除描述文件
        String result = CurlUtils.deleteProvisioningProfile(cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), ppVO.getProvisioningProfileId(), dsessionId);
        //检查执行请求421
        logger.info("删除描述文件：" + appleAccountInfo.getTeamId());
        TempSessionVO ts = checkRequest421(result, CommonConst.HINT_DELETE, cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), dsessionId);
        //表示是421重新获取到会话信息再请求
        if (CommonConst.NUMBER_1 == ts.getIs421()) {
            result = CurlUtils.deleteProvisioningProfile(cookie, ts.getCsrf(), ts.getCsrfTs(), appleAccountInfo.getTeamId(), ppVO.getProvisioningProfileId(), ts.getDsessionId());
            //是否成功
            isSuccess(result, CommonConst.HINT_DELETE, appleAccountInfo.getTeamId());
        }
        //创建描述文件
        logger.info("重建描述文件：" + appleAccountInfo.getTeamId());
        result = CurlUtils.regenProvisioningProfile(cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), ppVO.getProvisioningProfileId(), ppVO.getName(), ppVO.getUUID(), ppVO.getCertificateId(), appIdId, listDevice.toString(), dsessionId);
        ts = checkRequest421(result, CommonConst.HINT_INSER, cookie, csrf, csrf_ts, appleAccountInfo.getTeamId(), dsessionId);
        //表示是421重新获取到会话信息再请求
        if (CommonConst.NUMBER_1 == ts.getIs421()) {
            result = CurlUtils.regenProvisioningProfile(cookie, ts.getCsrf(), ts.getCsrfTs(), appleAccountInfo.getTeamId(), ppVO.getProvisioningProfileId(), ppVO.getName(), ppVO.getUUID(), ppVO.getCertificateId(), appIdId, listDevice.toString(), ts.getDsessionId());
            //是否成功
            isSuccess(result, CommonConst.HINT_INSER, appleAccountInfo.getTeamId());
            //重置描述文件之后重置描述文件Id
            ppVO.setProvisioningProfileId(resetProvisioningProfileId(result));
        } else {
            ppVO.setProvisioningProfileId(resetProvisioningProfileId(result));
        }
        logger.info("重置描述文件结束：" + result);
    }

    /**
     * @Author XIAOEN
     * @Description 保存数据
     * @Date 2018/12/24 15:34
     * @Param [appleAccountInfo, identifier, filePaths, deviceNumbers]
     * @return void
     **/
    public void insertDate(AppleAccountInfoVO appleAccountInfo, String identifier, String filePaths, String deviceNumbers) {
        //把设备号插入到数据库中
        AppleUdidVO appleUdidVO = new AppleUdidVO();
        appleUdidVO.setDeviceNumbers(deviceNumbers);
        appleUdidVO.setTeamId(appleAccountInfo.getTeamId());
        iAppleUdidService.insertAppleUdid(appleUdidVO);
        //保存应用下载信息
        AppleAppididVO appleAppididVO = new AppleAppididVO();
        appleAppididVO.setTeamId(appleAccountInfo.getTeamId());
        appleAppididVO.setAppleIdId(identifier);//保存证书标识符
        List<AppleAppididVO> appleAppididVOS = iAppleAppididService.listAppleAppidid(appleAppididVO);
        //一个设备只能有一个相同的应用，已存在就不添加，根据合作号和证书id查询
        if (appleAppididVOS.size() == CommonConst.NUMBER_0) {
            int startIndex = filePaths.indexOf(CommonConst.APPLICATION_NAME);
            appleAppididVO.setAppleididPath(SHELL_PATHS + filePaths.substring(startIndex + CommonConst.APPLICATION_LENGTH) + "downLoad.html");//下载路径
            iAppleAppididService.insertAppleAppidid(appleAppididVO);
        }
        //获取当前账号下添加的设备号数量，单个账号最多100个设备号
        AppleUdidVO appleUdid = new AppleUdidVO();
        appleUdid.setTeamId(appleAccountInfo.getTeamId());
        List<AppleUdidVO> listAppleUdidDevices = iAppleUdidService.listAppleUdid(appleUdid);
        int size = listAppleUdidDevices.size();
        //一个开发者账号最多能添加100个设备号
        if (size == CommonConst.DEVICE_LENGTH) {
            logger.info("该开发者账号设备已加满：" + appleAccountInfo.getTeamId() + size);
            //当前开发者账号加满100个设备号设为禁用
            appleAccountInfo.setIsEnable(0);
            iAppleAccountInfoService.updateAppleAccountInfoIsSession(appleAccountInfo);
        }
    }

}
