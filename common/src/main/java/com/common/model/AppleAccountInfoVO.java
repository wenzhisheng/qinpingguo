package com.common.model;

import com.common.model.base.AdminBaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

public class AppleAccountInfoVO extends AdminBaseVO implements Serializable {

    private static final long serialVersionUID = -269729379442987385L;

    /** 主键ID */
    @ApiModelProperty(value="主键ID")
    private Integer appleAccountInfoId;
    @ApiModelProperty(value="删除接收字符串Id")
    private String Ids;
    @ApiModelProperty(value="删除数组")
    private String[] appleAccountInfoIds;
    /** 合作号 */
    @ApiModelProperty(value="合作号")
    private String teamId;
    /** 会话cookie */
    @ApiModelProperty(value="会话(cookie)")
    private String cookieValue;
    /** 会话Id(DSESSIONID) */
    @ApiModelProperty(value="会话Id(DSESSIONID)")
    private String dsessionId;
    /** 跨站请求伪造csrf */
    @ApiModelProperty(value="跨站请求伪造(csrf)")
    private String csrf;
    /** 时间戳csrf_ts */
    @ApiModelProperty(value="时间戳(csrf_ts)")
    private String csrfTs;
    /** 保存文件路径 */
    @ApiModelProperty(value="保存文件路径")
    private String filePath;
    /** 脚本路径 */
    @ApiModelProperty(value="脚本路径")
    private String shellPath;
    /** 是否启用 0:未启用 1:启用 */
    @ApiModelProperty(value="是否启用 0:未启用 1:启用")
    private Integer isEnable;
    /** 是否会话 0:过期 1:正常 */
    @ApiModelProperty(value="是否会话 0:过期 1:正常")
    private Integer isSession;
    /** 设备长度 */
    @ApiModelProperty(value="设备长度")
    private Integer deviceLength;

    @Override
    public String toString() {
        return "AppleAccountInfoVO{" +
                "appleAccountInfoId=" + appleAccountInfoId +
                ", Ids='" + Ids + '\'' +
                ", appleAccountInfoIds=" + Arrays.toString(appleAccountInfoIds) +
                ", teamId='" + teamId + '\'' +
                ", cookieValue='" + cookieValue + '\'' +
                ", dsessionId='" + dsessionId + '\'' +
                ", csrf='" + csrf + '\'' +
                ", csrfTs='" + csrfTs + '\'' +
                ", filePath='" + filePath + '\'' +
                ", shellPath='" + shellPath + '\'' +
                ", isEnable=" + isEnable +
                ", isSession=" + isSession +
                ", deviceLength=" + deviceLength +
                '}';
    }

    public Integer getDeviceLength() {
        return deviceLength;
    }

    public void setDeviceLength(Integer deviceLength) {
        this.deviceLength = deviceLength;
    }

    public Integer getIsSession() {
        return isSession;
    }

    public void setIsSession(Integer isSession) {
        this.isSession = isSession;
    }

    public Integer getAppleAccountInfoId() {
        return appleAccountInfoId;
    }

    public void setAppleAccountInfoId(Integer appleAccountInfoId) {
        this.appleAccountInfoId = appleAccountInfoId;
    }

    public String getIds() {
        return Ids;
    }

    public void setIds(String ids) {
        Ids = ids;
    }

    public String[] getAppleAccountInfoIds() {
        return appleAccountInfoIds;
    }

    public void setAppleAccountInfoIds(String[] appleAccountInfoIds) {
        this.appleAccountInfoIds = appleAccountInfoIds;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public String getDsessionId() {
        return dsessionId;
    }

    public void setDsessionId(String dsessionId) {
        this.dsessionId = dsessionId;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }

    public String getCsrfTs() {
        return csrfTs;
    }

    public void setCsrfTs(String csrfTs) {
        this.csrfTs = csrfTs;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getShellPath() {
        return shellPath;
    }

    public void setShellPath(String shellPath) {
        this.shellPath = shellPath;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
