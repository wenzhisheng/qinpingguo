package com.common.adminuser.service;

import com.common.model.AdminUserVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAdminUserService {

    /**
     * @Author XIAOEN
     * @Description 登录
     * @Date 2018/11/8 14:40
     * @Param [adminUser]
     * @return com.common.model.AdminUserVO
     **/
     AdminUserVO login(AdminUserVO adminUser);

}