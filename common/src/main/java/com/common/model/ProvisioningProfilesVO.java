package com.common.model;

/**
 * @Author XIAOEN
 * @Description 配置文件属性
 * @Date 2018/11/8 14:28
 **/
public class ProvisioningProfilesVO {

    private static final long serialVersionUID = 2591674651085239957L;

    //描述文件Id
    private String provisioningProfileId;
    //证书Id和名称
    private String certificateIdAndName;
    //描述文件名称
    private String name;
    //描述文件请求Id(requestId)
    private String UUID;
    //证书Id
    private String certificateId;
    //苹果唯一应用ID
    String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getProvisioningProfileId() {
        return provisioningProfileId;
    }

    public void setProvisioningProfileId(String provisioningProfileId) {
        this.provisioningProfileId = provisioningProfileId;
    }

    public String getCertificateIdAndName() {
        return certificateIdAndName;
    }

    public void setCertificateIdAndName(String certificateIdAndName) {
        this.certificateIdAndName = certificateIdAndName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
}