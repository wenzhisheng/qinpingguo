package com.common.model;

import com.common.model.base.AdminBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author XIAOEN
 * @Description 用户管理
 * @Date 2018/11/8 14:28
 **/
@ApiModel(value="用户管理",description="用户管理")
public class AdminUserVO extends AdminBaseVO {

    private static final long serialVersionUID = 2591674651086349957L;
    /** 主键 */
    @ApiModelProperty(value="主键")
    private Integer userId;
    @ApiModelProperty(value="删除传值Id数组")
    private Integer[] userIds;
    /** 管理员账号 */
    @ApiModelProperty(value="管理员账号",example="xingguo")
    private String userAccount;
    /** 管理员密码 */
    @ApiModelProperty(value="管理员密码")
    private String userPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Integer[] userIds) {
        this.userIds = userIds;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}