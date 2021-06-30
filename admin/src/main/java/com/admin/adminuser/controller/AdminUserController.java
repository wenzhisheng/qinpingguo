package com.admin.adminuser.controller;

import com.common.adminuser.service.IAdminUserService;
import com.common.model.AdminUserVO;
import com.common.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/adminUser")
@Api(value="AdminUser Controller", tags="AdminUser Controller", description="用户管理")
public class AdminUserController {

    @Autowired
    private IAdminUserService iAdminUserService;

    /**
     * @Author XIAOEN
     * @Description 管理员登录接口
     * @Date 2018/11/8 20:24
     * @Param [adminUser]
     * @return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value="管理员登录接口",notes="管理员登录接口: 管理员账号必填，管理员密码必填")
    public Object login(@RequestBody @ApiParam(required = true) AdminUserVO adminUser){
        if (adminUser.getUserAccount() == null && adminUser.getUserPassword() == null){
            return CommonUtil.ERROR + "账号密码不能为空!";
        }
        AdminUserVO user = iAdminUserService.login(adminUser);
        //表示账号或密码错误
        if(user == null){
            return CommonUtil.ERROR + "账号或密码错误!";
        }else{
            return "登陆成功";
        }
    }

}