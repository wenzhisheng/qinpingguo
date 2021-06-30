package com.common.model;

import com.common.model.base.AdminBaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

public class AppleUdidVO extends AdminBaseVO implements Serializable {

    private static final long serialVersionUID = -269729379442987385L;

    /** 主键ID */
    @ApiModelProperty(value="主键ID")
    private Integer appleUdidId;
    @ApiModelProperty(value="删除接收字符串Id")
    private String Ids;
    @ApiModelProperty(value="删除数组")
    private String[] appleUdidIds;
    /** 合作号 */
    @ApiModelProperty(value="合作号")
    private String teamId;
    /** UDID */
    @ApiModelProperty(value="UDID")
    private String deviceNumbers;

    @Override
    public String toString() {
        return "AppleUdidVO{" +
                "appleUdidId=" + appleUdidId +
                ", Ids='" + Ids + '\'' +
                ", appleUdidIds=" + Arrays.toString(appleUdidIds) +
                ", teamId='" + teamId + '\'' +
                ", deviceNumbers='" + deviceNumbers + '\'' +
                '}';
    }

    public Integer getAppleUdidId() {
        return appleUdidId;
    }

    public void setAppleUdidId(Integer appleUdidId) {
        this.appleUdidId = appleUdidId;
    }

    public String getIds() {
        return Ids;
    }

    public void setIds(String ids) {
        Ids = ids;
    }

    public String[] getAppleUdidIds() {
        return appleUdidIds;
    }

    public void setAppleUdidIds(String[] appleUdidIds) {
        this.appleUdidIds = appleUdidIds;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDeviceNumbers() {
        return deviceNumbers;
    }

    public void setDeviceNumbers(String deviceNumbers) {
        this.deviceNumbers = deviceNumbers;
    }
}
