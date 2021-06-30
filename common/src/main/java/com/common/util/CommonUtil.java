package com.common.util;

import com.common.exception.DescribeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class CommonUtil {

    public final static Logger logger = LogManager.getLogger(CommonUtil.class);

    /**
     *失败状态 -1
     */
    public static final String STATUS_FAIL = "-1";
    /**
     *成功状态 1
     */
    public static final String STATUS_SUCCESS = "1";
    /**返回错误码标识*/
    public static final String ERROR = "ERROR:";

    public static void main(String args[]){
        System.out.println(md5Password16("123"));
    }
    /**
     * 生成32位md5码
     * @param password
     * @return
     */
    public static String md5Password(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * encrypt32
     * @param encryptStr
     * @return String
     */
    public static String md5Password16(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr.substring(8,24);
    }

    /**
     * @Description 得到nginx代理的IP
     * @Author Json
     * @DateTime 2018/4/8 21:02
     */
    public static String getIp(HttpServletRequest request)  {
        String ipAddress = request.getHeader("X-Real-IP");
        return  ipAddress;
    }

    /**
     * @Description 将xml格式的字符串解析成相应的是实体
     * @Author ABin
     * @DateTime 2018/4/15 20:34
     * @return java.lang.Object
     */
    public static Object XMLStringToBean(String xmlStr, Class cls) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xmlStr));
    }

    /**
     * @Description 将对象实体解析成xml格式的字符串
     * @Author ABin
     * @DateTime 2018/4/15 21:06
     * @return java.lang.String
     */
    public static String beanToXMLString(Object obj) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(obj, stringWriter);
        return stringWriter.toString();
    }


    /**
     * @Description 全局查找替换str的字符串
     * @Author Json
     * @DateTime 2018/5/2 13:33
     */
    public static String replaceAllStr(String str,String replaceStr,String rep){
        Pattern pattern = Pattern.compile(rep); //去掉空格符合换行符
        Matcher matcher = pattern.matcher(str);
        String result = matcher.replaceAll(replaceStr);
        return result;
    }

    /**
     * @Author ABin
     * @Description 参数非空校验
     * @DateTime 2018/5/18 19:24
     * @return void
     */
    public static void paramEmptyVerify(String param, String error) {
        if(StringUtils.isEmpty(param)) {
            throw new DescribeException(error, 0);
        }
    }

    /**
     * @Description 获取订单号月份
     * @Author Json
     * @DateTime 2018/5/20 16:57
     */
    public static String getSuffix(String orderNo){
        CommonUtil.paramEmptyVerify(orderNo, "订单号不能为空");
        // 正则校验订单号是否正确（R开头：存款  W开头：取款）
        if(!orderNo.matches("^[C,D,K,R,W]\\d+-\\d{18,}$")) {
            throw new DescribeException("订单号错误", 0);
        }
        String suffix = orderNo.substring(orderNo.length()-17, orderNo.length()-11);
        return suffix;
    }

    /**
     * @Description 用星号替换银行卡号
     * @Author ABin
     * @DateTime 2018/5/21 18:57
     */
    public static String rankCodeReplace(String rankCode){
        if(StringUtils.isEmpty(rankCode)) {
            return rankCode;
        }
        // 将银行卡号中间部分替换成星号
        StringBuffer stringBuffer = new StringBuffer(rankCode);
        return stringBuffer.replace(4, stringBuffer.length()-4, "********").toString();
    }

    /**
     * @Author AJie
     * @Description 生成商户编号（前缀(M) + "-" + 用户ID(01001)
     * @DateTime 2018/7/29 15:41
     * @param prefix , userId
     * @return java.lang.String
     */
    public static String generatemerchantCode(String prefix, String dateStr ,String userId) {
        StringBuffer merchantCode = new StringBuffer(prefix);
        merchantCode.append(dateStr)
                    .append(userId);
        return merchantCode.toString();
    }
    /**
     * @Description 返回一个随机类名
     * @Author Json
     * @DateTime 2018/7/12 17:25
     */
    public static String getClassName(){
        return  "com.common.util.A"+UUID.randomUUID().toString().replace("-","");
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                // 左补0
                sb.append("0").append(str);
                //右补0
                // sb.append(str).append("0");
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * @Description 创建一个在范围内的随机数
     * @Author Json
     * @DateTime 2018/7/29 19:13
     */
    public static Integer createRandomKey(Integer minVal, Integer maxVal) {
        Integer v  = new Random().nextInt(maxVal);
        if(v <= minVal) {
            v = v +minVal;
        }
        return v;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常" + e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                logger.error("IO流异常" + ex);
            }
        }
        return result;
    }

    /****
     * 随机商品
     * @return
     */
    public static String commodity(){
            String[] commodityName ={"50°古井淡雅酒青花 450ml","52°红星珍品蓝花瓷 500ml","52°小糊涂仙 500ml","42°天佑德青稞酒海拔2600 500ml","52°泸州老窖头曲 500ml","52°剑南春 500ml","46°洋河天之蓝 480ml","石库门上海老酒红标 500ml","黄尾袋鼠西拉 750ml",
                    "坦利橡树酒庄-北海岸黑皮诺干红葡萄酒 2013 750ml","康迪干白葡萄酒 750ml","澳洲奔富酒园BIN8红葡萄酒 750ml","小龙船红葡萄酒 375ml","法国乐船干红 750ml","马克斯威-精选美国加利福尼亚州纳帕谷美乐干红-2013 750ml","尊尼获加黑方威士忌 700ml",
                    "bacardi百加得白朗姆酒 750ml","百龄坛特醇苏格兰威士忌 700ml","杰克丹尼威士忌 700ml","绝对伏特加原味 700ml","百利甜爱尔兰 750ml","深蓝牌SKYY原味伏特加 750ml","皇家贝斯美乐威士忌 700ml"};
            return commodityName[(int)(Math.random() * commodityName.length)];
    }

    /**
     * 根据订单失效时间生产mq延时等级
     * */
    public static int getMqDelayLevel(int  delayTime){
        //  messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        if(delayTime >=30 && delayTime <= 60){
            return 4;
        } else if(delayTime >=60 && delayTime < 120){
            return 5;
        } else if(delayTime >=120 && delayTime < 180){
            return 6;
        }else if(delayTime >=180 && delayTime < 240){
            return 7;
        }else if(delayTime >=240 && delayTime < 300){
            return 8;
        }else if(delayTime >=300 && delayTime < 360){
            return 9;
        }else{
            return 6;
        }
    }

    /**
     * 校验手机号码
     * @Description
     * @Author xiaoen
     * @DateTime 2018/9/16 0001 22:26
     * @param
     * @return
     */
    public static void checkMobileNumber(String mobileVerification){
        String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if(null==mobileVerification){
            throw new DescribeException("手机号不能为空",-1);
        }
        if(!Pattern.matches(REGEX_MOBILE, mobileVerification)){
            throw new DescribeException("手机号格式不对",-1);
        }
    }

    /**
     * 获取登录IP地址
     * @Author XIAOEN
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 判断IP地址的合法性，这里采用了正则表达式的方法来判断
     * return true，合法
     * */
    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }
}
