package com.common.appleudid.service;

import com.common.model.AppleUdidVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 设备号Service
 * @Date 2018/11/7 15:12
 **/
public interface IAppleUdidService {

    /**
     * @Author XIAOEN
     * @Description 新增设备号
     * @Date 2018/11/8 20:11
     * @Param [appleUdidVO]
     * @return int
     **/
    int insertAppleUdid(AppleUdidVO appleUdidVO);

    /**
     * @Author XIAOEN
     * @Description 分页查询设备号
     * @Date 2018/11/8 20:11
     * @Param [appleUdidVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleUdidVO>
     **/
    PageResult<AppleUdidVO> findByAppleUdidPage(AppleUdidVO appleUdidVO, PageVO pageVO);

    /**
     * @Author XIAOEN
     * @Description 列表查询设备号
     * @Date 2018/11/8 20:23
     * @Param [appleUdidVO]
     * @return java.util.List<com.common.model.AppleUdidVO>
     **/
    List<AppleUdidVO> listAppleUdid(AppleUdidVO appleUdidVO);

    /**
     * @Author XIAOEN
     * @Description 更新苹果设备号
     * @Date 2018/11/8 20:11
     * @Param [appleUdidVO]
     * @return int
     **/
    int updateAppleUdid(AppleUdidVO appleUdidVO);

    /**
     * @Author XIAOEN
     * @Description 删除设备号
     * @Date 2018/11/8 20:11
     * @Param [appleUdidVO]
     * @return int
     **/
    int deleteAppleUdid(AppleUdidVO appleUdidVO);

}