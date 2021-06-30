package com.common.appleappidid.service.impl;

import com.common.appleappidid.dao.IAppleAppididDao;
import com.common.appleappidid.service.IAppleAppididService;
import com.common.model.AppleAppididVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 应用信息
 * @Date 2018/11/7 15:12
 **/
@Service
@Transactional
public class AppleAppididService implements IAppleAppididService {

    public  static Logger logger = LogManager.getLogger(AppleAppididService.class);

    @Autowired
    private IAppleAppididDao iAppleAppididDao;

    /**
     * @Author XIAOEN
     * @Description 新增应用信息
     * @Date 2018/11/8 20:19
     * @Param [appleAppididVO]
     * @return int
     **/
    @Override
    public int insertAppleAppidid(AppleAppididVO appleAppididVO){
        return iAppleAppididDao.insertAppleAppidid(appleAppididVO);
    }

    /**
     * @Author XIAOEN
     * @Description 分页查询应用信息
     * @Date 2018/11/8 20:19
     * @Param [appleAppididVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleAppididVO>
     **/
    @Override
    public PageResult<AppleAppididVO> findByAppleAppididPage(AppleAppididVO appleAppididVO, PageVO pageVO){
        return iAppleAppididDao.findByAppleAppididPage(appleAppididVO, pageVO);
    }

    /**
     * @Author XIAOEN
     * @Description 列表查询应用信息
     * @Date 2018/11/8 20:19
     * @Param [appleAppididVO]
     * @return java.util.List<com.common.model.AppleAppididVO>
     **/
    @Override
    public List<AppleAppididVO> listAppleAppidid(AppleAppididVO appleAppididVO){
        return iAppleAppididDao.listAppleAppidid(appleAppididVO);
    }

    /**
     * @Author XIAOEN
     * @Description 更新应用信息
     * @Date 2018/11/8 20:14
     * @Param [appleAppididVO]
     * @return int
     **/
    @Override
    public int updateAppleAppidid(AppleAppididVO appleAppididVO){
        return iAppleAppididDao.updateAppleAppidid(appleAppididVO);
    }

    /**
     * @Author XIAOEN
     * @Description 删除应用信息
     * @Date 2018/11/8 20:14
     * @Param [appleAppididVO]
     * @return int
     **/
    @Override
    public int deleteAppleAppidid(AppleAppididVO appleAppididVO){
        String ids = appleAppididVO.getIds();//把字符串Id转为数组
        if(ids != null){
            appleAppididVO.setAppleAppidids(ids.split(","));
        }
        return iAppleAppididDao.deleteAppleAppidid(appleAppididVO);
    }

}