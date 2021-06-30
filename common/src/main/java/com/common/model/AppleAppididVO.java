package com.common.model;

import com.common.model.base.AdminBaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

public class AppleAppididVO extends AdminBaseVO implements Serializable {

    private static final long serialVersionUID = -269729379442987385L;

    /** 主键ID */
    @ApiModelProperty(value="应用信息主键ID")
    private Integer appleAppidid;
    @ApiModelProperty(value="删除接收字符串Id")
    private String Ids;
    @ApiModelProperty(value="删除数组")
    private String[] appleAppidids;
    /** 合作号 */
    @ApiModelProperty(value="合作号")
    private String teamId;
    /** 应用编号 */
    @ApiModelProperty(value="应用编号")
    private String appleIdId;
    /** 应用下载路径 */
    @ApiModelProperty(value="应用下载路径")
    private String appleididPath;

    @Override
    public String toString() {
        return "AppleAppididVO{" +
                "appleAppidid=" + appleAppidid +
                ", Ids='" + Ids + '\'' +
                ", appleAppidids=" + Arrays.toString(appleAppidids) +
                ", teamId='" + teamId + '\'' +
                ", appleIdId='" + appleIdId + '\'' +
                ", appleididPath='" + appleididPath + '\'' +
                '}';
    }

    public Integer getAppleAppidid() {
        return appleAppidid;
    }

    public void setAppleAppidid(Integer appleAppidid) {
        this.appleAppidid = appleAppidid;
    }

    public String getIds() {
        return Ids;
    }

    public void setIds(String ids) {
        Ids = ids;
    }

    public String[] getAppleAppidids() {
        return appleAppidids;
    }

    public void setAppleAppidids(String[] appleAppidids) {
        this.appleAppidids = appleAppidids;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getAppleIdId() {
        return appleIdId;
    }

    public void setAppleIdId(String appleIdId) {
        this.appleIdId = appleIdId;
    }

    public String getAppleididPath() {
        return appleididPath;
    }

    public void setAppleididPath(String appleididPath) {
        this.appleididPath = appleididPath;
    }
}
