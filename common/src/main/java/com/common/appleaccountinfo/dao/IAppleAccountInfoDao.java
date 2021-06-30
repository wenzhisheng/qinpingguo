package com.common.appleaccountinfo.dao;

import com.common.model.AppleAccountInfoVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 苹果账号信息dao
 * @Date 2018/11/7 15:12
 **/
public interface IAppleAccountInfoDao {

    /**
     * @Author XIAOEN
     * @Description 提交苹果账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    int insertAppleAccountInfo(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 分页查询苹果账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleAccountInfoVO>
     **/
    PageResult<AppleAccountInfoVO> findByAppleAccountInfoPage(@Param("vo") AppleAccountInfoVO appleAccountInfoVO, @Param("page") PageVO pageVO);

    /**
     * @Author XIAOEN
     * @Description 查询账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return java.util.List<com.common.model.AppleAccountInfoVO>
     **/
    AppleAccountInfoVO selectAppleAccountInfo(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 列表查询账号信息，定时任务遍历
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return java.util.List<com.common.model.AppleAccountInfoVO>
     **/
    List<AppleAccountInfoVO> listAppleAccountInfo(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 更新苹果账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    int updateAppleAccountInfo(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 更新苹果账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    int updateAppleAccountInfoIsSession(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 更新苹果账号信息421
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    int updateAppleAccountInfo421(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

    /**
     * @Author XIAOEN
     * @Description 删除苹果账号信息
     * @Date 2018/11/8 20:23
     * @Param [appleAccountInfoVO]
     * @return int
     **/
    int deleteAppleAccountInfo(@Param("vo") AppleAccountInfoVO appleAccountInfoVO);

}