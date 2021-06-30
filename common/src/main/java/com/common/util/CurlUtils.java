package com.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.constant.CommonConst;
import com.common.exception.DescribeException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author A jun
 * @ClassName com.common.util
 * @Description Curl调用工具类
 * @Date 2018/4/15 17:04
 * @Version 1.0
 */
@Component
public class CurlUtils {

    public static Logger logger = LogManager.getLogger(CurlUtils.class);

    public static String[] partitionCommandLine(final String command) {
        final ArrayList<String> commands = new ArrayList<>();
        int index = 0;
        StringBuffer buffer = new StringBuffer(command.length());
        boolean isApos = false;
        boolean isQuote = false;
        while (index < command.length()) {
            final char c = command.charAt(index);
            switch (c) {
                case ' ':
                    if (!isQuote && !isApos) {
                        final String arg = buffer.toString();
                        buffer = new StringBuffer(command.length() - index);
                        if (arg.length() > 0) {
                            commands.add(arg);
                        }
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '\'':
                    if (!isQuote) {
                        isApos = !isApos;
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '"':
                    if (!isApos) {
                        isQuote = !isQuote;
                    } else {
                        buffer.append(c);
                    }
                    break;
                default:
                    buffer.append(c);
            }
            index++;
        }
        if (buffer.length() > 0) {
            final String arg = buffer.toString();
            commands.add(arg);
        }
        return commands.toArray(new String[commands.size()]);
    }

    /**
     * @Author XIAOEN
     * @Description 编辑配置文件
     * @Date 2018/11/4 21:36
     * @Param [cookie, csrf, teamId]
     * @return java.lang.String
     **/
    public static String regenProvisioningProfile(String cookie, String csrf, String csrf_ts, String teamId, String provisioningProfileId,
        String name, String UUID, String certificateIds, String listAppId, String listDevices, String DSESSIONID) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{10}\" -d \"provisioningProfileId={4}&distributionType=limited&returnFullObjects=false&provisioningProfileName={5}&appIdId={8}&certificateIds={7}{9}\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/regenProvisioningProfile.action?content-type=text/x-url-arguments&accept=application/json&requestId={6}&userLocale=en_US&teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,provisioningProfileId,name,UUID,certificateIds,listAppId,listDevices,DSESSIONID);
        ProcessBuilder pb = new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("regenProvisioningProfile curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("regenProvisioningProfile result is {}",controlLogInput(result));
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
     * @Description 删除配置文件
     * @Date 2018/11/4 21:36
     * @Param [cookie, csrf, teamId]
     * @return java.lang.String
     **/
    public static String deleteProvisioningProfile(String cookie, String csrf, String csrf_ts, String teamId, String provisioningProfileId, String DSESSIONID) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{5}\" -d \"provisioningProfileId={4}&teamId={3}\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/deleteProvisioningProfile.action?teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,provisioningProfileId,DSESSIONID);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("deleteProvisioningProfile curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("deleteProvisioningProfile result is {}",controlLogInput(result));
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
     * @Description 获取certificateIds
     * @Date 2018/11/4 21:36
     * @Param [cookie, csrf, teamId]
     * @return java.lang.String
     **/
    public static JSONObject getProvisioningProfile(String cookie, String csrf, String csrf_ts, String teamId, String provisioningProfileId, String dsessionId) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{5}\" -d \"includeInactiveProfiles=true&provisioningProfileId={4}&teamId={3}&nd=1541486988562&pageNumber=1&pageSize=500\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/getProvisioningProfile.action?teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,provisioningProfileId,dsessionId);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("getProvisioningProfile curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("getProvisioningProfile result is {}",controlLogInput(result));
            p.waitFor();
            p.destroy();
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            checkRequest(result, CommonConst.LOGIN_INFO_CLOSE,"获取描述文件信息失败：");
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject provisioningProfile = (JSONObject)jsonObject.get("provisioningProfile");
            return provisioningProfile;
        }finally {
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 获取appIdId
     * @Date 2018/11/4 21:36
     * @Param [cookie, csrf, teamId]
     * @return java.lang.String
     **/
    public static String getlistAppIds(String cookie, String csrf, String teamId, String csrf_ts) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{3}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}\" -d \"pageNumber=1&pageSize=500&sort=name%3dasc&onlyCountLists=true\" \"https://developer.apple.com/services-account/QH65B2/account/ios/identifiers/listAppIds.action?content-type=text/x-url-arguments&accept=application/json&requestId=f8907781-5840-41e0-y793-5242b34735ed&userLocale=en_US&teamId={2}\"";
        String str = MessageFormat.format(url,cookie,csrf,teamId,csrf_ts);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("getlistAppIds curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("getlistAppIds result is {}",controlLogInput(result));
            p.waitFor();
            p.destroy();
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            checkRequest(result,"登录信息失效","获取appIdId失败：");
            return result;
        }finally {
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 获取所有设备号Id
     * @Date 2018/11/4 21:36
     * @Param [cookie, csrf, teamId]
     * @return java.lang.String
     **/
    public static List<String> getListDevicesId(String cookie, String csrf, String csrf_ts, String teamId, String dsessionId) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{4}\" -d \"includeRemovedDevices=true&includeAvailability=true&sidx=status&sort=status%3dasc&teamId={3}&nd=1541486988562&pageNumber=1&pageSize=500\" \"https://developer.apple.com/services-account/QH65B2/account/ios/device/listDevices.action?content-type=text/x-url-arguments&accept=application/json&requestId=12880f4f-9205-46e2-yffa-afe4d523fa63&userLocale=en_US&teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,dsessionId);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("getListDevices curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("getListDevices result is {}",controlLogInput(result));
            p.waitFor();
            p.destroy();
            int start = result.indexOf(CommonConst.BRACE_LEFT);
            result = result.substring(start);
            checkRequest(result,"登录信息失效","获取所有添加设备号失败：");
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray devices = (JSONArray)jsonObject.get(CommonConst.JSON_KEY_DEVICES);
            List<String> listDevices = new ArrayList<String>();
            for (int i=0;i<devices.size();i++){
                JSONObject deviceId = (JSONObject)devices.get(i);
                listDevices.add((String)deviceId.get(CommonConst.JSON_KEY_DEVICEID));
            }
            return listDevices;
        }finally {
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 检查账号苹果登录信息
     * @Date 2018/11/4 21:36
     * @Param [cookie, csrf, teamId]
     * @return java.lang.String
     **/
    public static String checkAccount(String cookie, String csrf, String csrf_ts, String teamId, String dsessionId) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{4}\" -d \"includeInactiveProfiles=true&onlyCountLists=true&sidx=name&sort=name%3dasc&teamId={3}&type=limited&pageNumber=1&pageSize=500\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/listProvisioningProfiles.action?teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,dsessionId);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("appleCheckAccount curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("appleCheckAccount result is {}",controlLogInput(result));
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
     * @Description 调用CRUL命令添加设备
     * @Date 2018/11/4 21:45
     * @Param [cookie, csrf, csrf_ts, teamId, deviceNumbers]
     * @return java.lang.String
     **/
    public static String addAccount(String cookie, String csrf, String csrf_ts, String teamId, String deviceNumbers, String deviceName, String DSESSIONID) throws  Exception{
        String url = "curl -i -L --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"csrf_ts:{2}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\"  -b \"{0}{6}\" -d \"deviceNames={5}&deviceNumbers={4}&register=single&teamId={3}\" \"https://developer.apple.com/services-account/QH65B2/account/ios/device/addDevices.action?teamId={3}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,deviceNumbers,deviceName, DSESSIONID);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("appleAddAccount curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("appleAddAccount result is {}",controlLogInput(result));
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
     * @Description 调用CRUL命令下载描述文件
     * @Date 2018/11/4 21:47
     * @Param [cookie, csrf, filePath, teamId, provisioningProfileId]
     * @return java.lang.String
     **/
    public static String downloadDescribeFile(String cookie, String csrf, String csrf_ts, String teamId, String filePath, String provisioningProfileId, String DSESSIONID) throws Exception{
        String url = "curl -o {4}freeCertificate.mobileprovision --create-dirs --compressed -s -H \"Accept-Encoding:gzip\" -H \"csrf:{1}\" -H \"Host:developer.apple.com\" -H \"Origin:https://developer.apple.com\" -H \"Referer:https://developer.apple.com/account/ios/device/create\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\" -H \"csrf_ts:{2}\" -b \"{0}{6}\" \"https://developer.apple.com/services-account/QH65B2/account/ios/profile/downloadProfileContent?teamId={3}&provisioningProfileId={5}\"";
        String str = MessageFormat.format(url,cookie,csrf,csrf_ts,teamId,filePath,provisioningProfileId,DSESSIONID);
        ProcessBuilder pb=new ProcessBuilder(CurlUtils.partitionCommandLine(str));
        logger.info("appleDownloadDescribeFile curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,CommonConst.ENCODING_UTF_8);
            logger.info("appleDownloadDescribeFile result is {}",controlLogInput(result));
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
     * @Description 检查执行请求
     * @Date 2018/11/6 22:34
     * @Param [result]
     * @return void
     **/
    public static void checkRequest(String result, String describe, String describe1){
        String resultStatus = result.substring(9, 12);
        if (CommonConst.APPLE_RESULT_STATUS_421.equals(resultStatus)){
            logger.info(describe+resultStatus);
        }else{
            if (result.contains(CommonConst.BRACE_LEFT)){
                int start = result.indexOf(CommonConst.BRACE_LEFT);
                result = result.substring(start);
                JSONObject jsonObject = JSONObject.parseObject(result);
                String resultCode = jsonObject.get(CommonConst.JSON_KEY_RESULTCODE).toString();
                if (!CommonConst.APPLE_RESULT_STATUS_0.equals(resultCode)){
                    logger.info(describe1+resultCode);
                }
            }else{
                logger.info("错误请求(Bad Request)");
                throw new DescribeException("Bad Request",-1);
            }
        }
    }

    /**
     * @Author XIAOEN
     * @Description 控制日志输出
     * @Date 2018/12/24 19:55
     * @Param [result]
     * @return java.lang.String
     **/
    public static String controlLogInput(String result){
        return result.length() >300 ? result.substring(0,300) : result;
    }

}
