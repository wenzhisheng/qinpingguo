package com.common.appleappidid.service;

import com.common.model.AppleAppididVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 应用信息
 * @Date 2018/11/7 15:12
 **/
public interface IAppleAppididService {

    /**
     * @Author XIAOEN
     * @Description 新增应用信息
     * @Date 2018/11/8 20:13
     * @Param [appleAppididVO]
     * @return int
     **/
    int insertAppleAppidid(AppleAppididVO appleAppididVO);

    /**
     * @Author XIAOEN
     * @Description 分页查询应用信息
     * @Date 2018/11/8 20:13
     * @Param [appleAppididVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleAppididVO>
     **/
    PageResult<AppleAppididVO> findByAppleAppididPage(AppleAppididVO appleAppididVO, PageVO pageVO);

    /**
     * @Author XIAOEN
     * @Description 列表查询账号信息
     * @Date 2018/11/8 20:13
     * @Param [appleAppididVO]
     * @return java.util.List<com.common.model.AppleAppididVO>
     **/
    List<AppleAppididVO> listAppleAppidid(AppleAppididVO appleAppididVO);

    /**
     * @Author XIAOEN
     * @Description 更新应用信息
     * @Date 2018/11/8 20:13
     * @Param [appleAppididVO]
     * @return int
     **/
    int updateAppleAppidid(AppleAppididVO appleAppididVO);

    /**
     * @Author XIAOEN
     * @Description 删除应用信息
     * @Date 2018/11/8 20:13
     * @Param [appleAppididVO]
     * @return int
     **/
    int deleteAppleAppidid(AppleAppididVO appleAppididVO);

}