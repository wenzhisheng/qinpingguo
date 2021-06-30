package com.common.appleudid.dao;

import com.common.model.AppleAccountInfoVO;
import com.common.model.AppleUdidVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 设备号
 * @Date 2018/11/7 15:12
 **/
public interface IAppleUdidDao {

    /**
     * @Author XIAOEN
     * @Description 新增设备号
     * @Date 2018/11/8 20:12
     * @Param [appleUdidVO]
     * @return int
     **/
    int insertAppleUdid(@Param("vo") AppleUdidVO appleUdidVO);

    /**
     * @Author XIAOEN
     * @Description 分页查询设备号
     * @Date 2018/11/8 20:12
     * @Param [appleUdidVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleUdidVO>
     **/
    PageResult<AppleUdidVO> findByAppleUdidPage(@Param("vo") AppleUdidVO appleUdidVO, @Param("page") PageVO pageVO);

    /**
     * @Author XIAOEN
     * @Description 列表查询设备号
     * @Date 2018/11/8 20:23
     * @Param [appleUdidVO]
     * @return java.util.List<com.common.model.AppleUdidVO>
     **/
    List<AppleUdidVO> listAppleUdid(@Param("vo") AppleUdidVO appleUdidVO);

    /**
     * @Author XIAOEN
     * @Description 更新设备号
     * @Date 2018/11/8 20:12
     * @Param [appleUdidVO]
     * @return int
     **/
    int updateAppleUdid(@Param("vo") AppleUdidVO appleUdidVO);

    /**
     * @Author XIAOEN
     * @Description 删除设备号
     * @Date 2018/11/8 20:12
     * @Param [appleUdidVO]
     * @return int
     **/
    int deleteAppleUdid(@Param("vo") AppleUdidVO appleUdidVO);

}