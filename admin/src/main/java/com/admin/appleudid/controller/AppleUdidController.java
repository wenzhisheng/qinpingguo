package com.admin.appleudid.controller;

import com.common.appleudid.service.IAppleUdidService;
import com.common.model.AppleUdidVO;
import com.common.model.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appleUdid")
@Api(value="AppleUdid Controller", tags="AppleUdid Controller", description="设备号")
public class AppleUdidController {

	@Autowired
	private IAppleUdidService iAppleUdidService;

	/**
	 * @Author XIAOEN
	 * @Description 添加设备号
	 * @Date 2018/11/8 13:50
	 * @Param [appleUdidVO]
	 * @return int
	 **/
	@PostMapping("/insertAppleAppidid")
	@ApiOperation(value = "新增设备号",notes = "新增设备号，appleUdidVO")
	public Object insertAppleUdid(@RequestBody @ApiParam(required = true) AppleUdidVO appleUdidVO){
		return iAppleUdidService.insertAppleUdid(appleUdidVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 分页查询设备号
	 * @Date 2018/11/8 20:32
	 * @Param [appleUdidVO, pageVO]
	 * @return java.lang.Object
	 **/
	@GetMapping("/findByAppleUdidPage")
	@ApiOperation(value = "分页查询设备号",notes = "新增设备号，appleUdidVO、pageVO")
	public Object findByAppleUdidPage(AppleUdidVO appleUdidVO, PageVO pageVO){
		return iAppleUdidService.findByAppleUdidPage(appleUdidVO, pageVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 更新设备号
	 * @Date 2018/11/8 20:32
	 * @Param [appleUdidVO]
	 * @return java.lang.Object
	 **/
	@PostMapping("/updateAppleUdid")
	@ApiOperation(value = "更新设备号",notes = "更新设备号，appleUdidVO")
	public Object updateAppleUdid(@RequestBody @ApiParam(required = true) AppleUdidVO appleUdidVO){
		return iAppleUdidService.updateAppleUdid(appleUdidVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 删除设备号
	 * @Date 2018/11/8 20:32
	 * @Param [appleUdidVO]
	 * @return java.lang.Object
	 **/
	@GetMapping("/deleteAppleUdid")
	@ApiOperation(value = "删除设备号",notes = "删除设备号，appleUdidVO")
	public Object deleteAppleUdid(AppleUdidVO appleUdidVO){
		return iAppleUdidService.deleteAppleUdid(appleUdidVO);
	}

}
