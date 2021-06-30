package com.common.appleudid.service.impl;

import com.common.appleudid.dao.IAppleUdidDao;
import com.common.appleudid.service.IAppleUdidService;
import com.common.model.AppleUdidVO;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author XIAOEN
 * @Description 苹果设备号Service实现
 * @Date 2018/11/7 15:12
 **/
@Service
@Transactional
public class AppleUdidService implements IAppleUdidService {

    public  static Logger logger = LogManager.getLogger(AppleUdidService.class);

    @Autowired
    private IAppleUdidDao iAppleUdidDao;

    /**
     * @Author XIAOEN
     * @Description 添加设备号
     * @Date 2018/11/8 20:10
     * @Param [appleUdidVO]
     * @return int
     **/
    @Override
    public int insertAppleUdid(AppleUdidVO appleUdidVO){
        return iAppleUdidDao.insertAppleUdid(appleUdidVO);
    }

    /**
     * @Author XIAOEN
     * @Description 分页查询设备号
     * @Date 2018/11/8 20:10
     * @Param [appleUdidVO, pageVO]
     * @return com.common.model.base.PageResult<com.common.model.AppleUdidVO>
     **/
    @Override
    public PageResult<AppleUdidVO> findByAppleUdidPage(AppleUdidVO appleUdidVO, PageVO pageVO){
        return iAppleUdidDao.findByAppleUdidPage(appleUdidVO, pageVO);
    }

    /**
     * @Author XIAOEN
     * @Description 列表查询设备号
     * @Date 2018/11/8 20:23
     * @Param [appleUdidVO]
     * @return java.util.List<com.common.model.AppleUdidVO>
     **/
    @Override
    public List<AppleUdidVO> listAppleUdid(AppleUdidVO appleUdidVO){
        return iAppleUdidDao.listAppleUdid(appleUdidVO);
    }

    /**
     * @Author XIAOEN
     * @Description 更新设备号
     * @Date 2018/11/8 20:10
     * @Param [appleUdidVO]
     * @return int
     **/
    @Override
    public int updateAppleUdid(AppleUdidVO appleUdidVO){
        return iAppleUdidDao.updateAppleUdid(appleUdidVO);
    }

    /**
     * @Author XIAOEN
     * @Description 删除设备号
     * @Date 2018/11/8 20:10
     * @Param [appleUdidVO]
     * @return int
     **/
    @Override
    public int deleteAppleUdid(AppleUdidVO appleUdidVO){
        String ids = appleUdidVO.getIds();//把字符串Id转为数组
        if(ids != null){
            appleUdidVO.setAppleUdidIds(ids.split(","));
        }
        return iAppleUdidDao.deleteAppleUdid(appleUdidVO);
    }

}