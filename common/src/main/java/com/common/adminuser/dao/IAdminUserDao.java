package com.common.adminuser.dao;

import com.common.model.AdminUserVO;
import org.apache.ibatis.annotations.Param;

public interface IAdminUserDao {

    /**
     * @Author XIAOEN
     * @Description 登录
     * @Date 2018/11/8 14:40
     * @Param [adminUser]
     * @return com.common.model.AdminUserVO
     **/
    AdminUserVO login(@Param("vo") AdminUserVO adminUser);

}