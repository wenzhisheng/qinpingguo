package com.common.appleaccountinfo.service;

import com.common.model.AppleAccountInfoVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 苹果账号信息Service
 * @Date 2018/11/7 15:12
 **/
public interface IAppleAccountInfoService {

    /**
     * @Author XIAOEN
     * @Description 提交苹果账号信息
     * @Date 2018/11/8 20:21
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    int insertAppleAccountInfo(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 分页查询苹果账号信息
     * @Date 2018/11/8 20:20
     * @Param [appleAccountInfoVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleAccountInfoVO>
     **/
    PageResult<AppleAccountInfoVO> findByAppleAccountInfoPage(AppleAccountInfoVO appleAccountInfoVO, PageVO pageVO);

    /**
     * @Author XIAOEN
     * @Description 查询账号信息
     * @Date 2018/11/8 20:20
     * @Param [appleAccountInfoVO]
     * @return java.util.List<com.common.model.AppleAccountInfoVO>
     **/
    AppleAccountInfoVO selectAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 列表查询账号信息，定时任务遍历
     * @Date 2018/11/8 20:20
     * @Param [appleAccountInfoVO]
     * @return java.util.List<com.common.model.AppleAccountInfoVO>
     **/
    List<AppleAccountInfoVO> listAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 处理苹果账号信息
     * @Date 2018/11/8 20:20
     * @Param [exceptionOrderVO]
     * @return int
     **/
    int updateAppleAccountInfo(AppleAccountInfoVO exceptionOrderVO);

    /**
     * @Author XIAOEN
     * @Description 更新会话状态
     * @Date 2018/11/8 20:20
     * @Param [exceptionOrderVO]
     * @return int
     **/
    int updateAppleAccountInfoIsSession(AppleAccountInfoVO exceptionOrderVO);

    /**
     * @Author XIAOEN
     * @Description 处理苹果账号信息421
     * @Date 2018/11/8 20:20
     * @Param [exceptionOrderVO]
     * @return int
     **/
    int updateAppleAccountInfo421(AppleAccountInfoVO exceptionOrderVO);

    /**
     * @Author XIAOEN
     * @Description 删除苹果账号
     * @Date 2018/11/8 20:20
     * @Param [exceptionOrderVO]
     * @return int
     **/
    int deleteAppleAccountInfo(AppleAccountInfoVO exceptionOrderVO);

}