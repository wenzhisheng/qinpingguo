package com.common.appleappidid.dao;

import com.common.model.AppleAppididVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 应用信息
 * @Date 2018/11/7 15:12
 **/
public interface IAppleAppididDao {

    /**
     * @Author XIAOEN
     * @Description 新增应用信息
     * @Date 2018/11/8 20:19
     * @Param [appleAppididVO]
     * @return int
     **/
    int insertAppleAppidid(@Param("vo") AppleAppididVO appleAppididVO);

    /**
     * @Author XIAOEN
     * @Description 分页查询应用信息
     * @Date 2018/11/8 20:19
     * @Param [appleAppididVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleAppididVO>
     **/
    PageResult<AppleAppididVO> findByAppleAppididPage(@Param("vo") AppleAppididVO appleAppididVO, @Param("page") PageVO pageVO);

    /**
     * @Author XIAOEN
     * @Description 列表查询应用信息
     * @Date 2018/11/8 20:19
     * @Param [appleAppididVO]
     * @return java.util.List<com.common.model.AppleAppididVO>
     **/
    List<AppleAppididVO> listAppleAppidid(@Param("vo") AppleAppididVO appleAppididVO);

    /**
     * @Author XIAOEN
     * @Description 更新应用信息
     * @Date 2018/11/8 20:20
     * @Param [appleAppididVO]
     * @return int
     **/
    int updateAppleAppidid(@Param("vo") AppleAppididVO appleAppididVO);

    /**
     * @Author XIAOEN
     * @Description 删除应用信息
     * @Date 2018/11/8 20:20
     * @Param [appleAppididVO]
     * @return int
     **/
    int deleteAppleAppidid(@Param("vo") AppleAppididVO appleAppididVO);

}