package com.common.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TempSessionVO implements Serializable {

    private static final long serialVersionUID = -269729363542987385L;

    /** 是否421：0:不是 1:是 */
    @ApiModelProperty(value="是否421：0:不是 1:是")
    private Integer is421;
    /** 跨站请求伪造csrf */
    @ApiModelProperty(value="跨站请求伪造(csrf)")
    private String csrf;
    /** 时间戳csrf_ts */
    @ApiModelProperty(value="时间戳(csrf_ts)")
    private String csrfTs;
    /** 会话Id(DSESSIONID) */
    @ApiModelProperty(value="会话Id(DSESSIONID)")
    private String dsessionId;

    public Integer getIs421() {
        return is421;
    }

    public void setIs421(Integer is421) {
        this.is421 = is421;
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

    public String getDsessionId() {
        return dsessionId;
    }

    public void setDsessionId(String dsessionId) {
        this.dsessionId = dsessionId;
    }
}
