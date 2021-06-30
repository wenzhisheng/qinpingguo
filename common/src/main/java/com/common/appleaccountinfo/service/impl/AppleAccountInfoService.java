package com.common.appleaccountinfo.service.impl;

import com.common.appleaccountinfo.dao.IAppleAccountInfoDao;
import com.common.appleaccountinfo.service.IAppleAccountInfoService;
import com.common.exception.DescribeException;
import com.common.model.AppleAccountInfoVO;
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
 * @Description 苹果账号信息Service实现
 * @Date 2018/11/7 15:12
 **/
@Service
@Transactional
public class AppleAccountInfoService implements IAppleAccountInfoService {

    public  static Logger logger = LogManager.getLogger(AppleAccountInfoService.class);

    @Autowired
    private IAppleAccountInfoDao iAppleAccountInfoDao;

    /**
     * @Author XIAOEN
     * @Description 提交苹果账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    @Override
    public int insertAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
        //只根据合作号查询是否存在
        AppleAccountInfoVO appleAccountInfoVO2 = new AppleAccountInfoVO();
        appleAccountInfoVO2.setTeamId(appleAccountInfoVO.getTeamId());
        AppleAccountInfoVO appleAccountInfoVO1 = iAppleAccountInfoDao.selectAppleAccountInfo(appleAccountInfoVO2);
        if (appleAccountInfoVO1 != null){
            throw new DescribeException("合作号已成在，不能重复创建",-1);
        }
        return iAppleAccountInfoDao.insertAppleAccountInfo(appleAccountInfoVO);
    }

    /**
     * @Author XIAOEN
     * @Description 分页查询苹果账号信息
     * @Date 2018/11/8 20:22
     * @Param [appleAccountInfoVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleAccountInfoVO>
     **/
    @Override
    public PageResult<AppleAccountInfoVO> findByAppleAccountInfoPage(AppleAccountInfoVO appleAccountInfoVO, PageVO pageVO){
        return iAppleAccountInfoDao.findByAppleAccountInfoPage(appleAccountInfoVO, pageVO);
    }

    /**
     * @Author XIAOEN
     * @Description 查询账号信息
     * @Date 2018/11/8 20:22
     * @Param [appleAccountInfoVO]
     * @return java.util.List<com.common.model.AppleAccountInfoVO>
     **/
    @Override
    public AppleAccountInfoVO selectAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
        return iAppleAccountInfoDao.selectAppleAccountInfo(appleAccountInfoVO);
    }

    /**
     * @Author XIAOEN
     * @Description 列表查询账号信息，定时任务遍历
     * @Date 2018/11/8 20:22
     * @Param [appleAccountInfoVO]
     * @return java.util.List<com.common.model.AppleAccountInfoVO>
     **/
    @Override
    public List<AppleAccountInfoVO> listAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
        return iAppleAccountInfoDao.listAppleAccountInfo(appleAccountInfoVO);
    }

    /**
     * @Author XIAOEN
     * @Description 处理苹果账号信息
     * @Date 2018/11/8 20:21
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    @Override
    public int updateAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
        return iAppleAccountInfoDao.updateAppleAccountInfo(appleAccountInfoVO);
    }

    /**
     * @Author XIAOEN
     * @Description 更新会话状态
     * @Date 2018/11/8 20:21
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    @Override
    public int updateAppleAccountInfoIsSession(AppleAccountInfoVO appleAccountInfoVO){
        return iAppleAccountInfoDao.updateAppleAccountInfoIsSession(appleAccountInfoVO);
    }

    /**
     * @Author XIAOEN
     * @Description 处理苹果账号信息
     * @Date 2018/11/8 20:21
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    @Override
    public int updateAppleAccountInfo421(AppleAccountInfoVO appleAccountInfoVO){
        return iAppleAccountInfoDao.updateAppleAccountInfo421(appleAccountInfoVO);
    }

    /**
     * @Author XIAOEN
     * @Description 删除苹果账号信息
     * @Date 2018/11/8 20:21
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    @Override
    public int deleteAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
        String ids = appleAccountInfoVO.getIds();//把字符串Id转为数组
        if(ids != null){
            appleAccountInfoVO.setAppleAccountInfoIds(ids.split(","));
        }
        return iAppleAccountInfoDao.deleteAppleAccountInfo(appleAccountInfoVO);
    }

}