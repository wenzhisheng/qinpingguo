package com.common.exception;

/**
 * @Author json
 * @Description 异常枚举
 * @Date 2018/4/7
 */
public enum ExceptionEnum {
    UNKNOW_ERROR(-1,"系统出现异常！"),
    USER_NOT_FIND(-2,"数据库插入数据异常！"),
    NOT_LOGIN_ERROR(401,"您的账号未登录，请登录后再操作！"),
    SINGLE_LOGIN_ERROR(3, "该账号已在其他地方登录，您被挤下线了，请重新登录。"),
    IMPORT_FILE_NULL_ERROR(-3, "导入的文件不能为空，请修改后再导入！"),
    IMPORT_FILE_FORMAT_ERROR(-3, "导入的文件格式错误，只支持导入后缀名为(.xls)和(.xlsx)的文件！"),
    IMPORT_FILE_NOT_FOUND_FIELD_ERROR(-3, "导入的Sheet工作簿中没有有效列，请修改后再导入！"),
    IMPORT_FILE_MAX_NUMBER_ERROR(-3, "导入的文件数量超过最大限制，请修改后再导入！"),
    PARAM_ERROR(-1,"请求参数非法！"),
    STATUS_407(407, "请求的域名无效"),
    UNKNOW_URL(-4,"请求的域名无效"),
    SIGN_INTERFACE_CLOSE_STATUS_511(511, "签到接口已关闭"),
    SIGN_IN_STATUS_512(512, "今天已签到");

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
