package com.common.adminuser.service.impl;

import com.common.adminuser.dao.IAdminUserDao;
import com.common.adminuser.service.IAdminUserService;
import com.common.model.AdminUserVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AdminUserService implements IAdminUserService {

    public  static Logger logger = LogManager.getLogger(AdminUserService.class);

    @Autowired
    private IAdminUserDao iAdminUserDao;

    /**
     * @Author XIAOEN
     * @Description 登录
     * @Date 2018/11/8 14:39
     * @Param [adminUser]
     * @return com.common.model.AdminUserVO
     **/
    @Override
    public AdminUserVO login(AdminUserVO adminUser) {
        return iAdminUserDao.login(adminUser);
    }

}
