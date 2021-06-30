package com.admin.appleaccountinfo.controller;

import com.common.appleaccountinfo.service.IAppleAccountInfoService;
import com.common.model.AppleAccountInfoVO;
import com.common.model.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appleAccountInfo")
@Api(value="AppleAccountInfo Controller", tags="AppleAccountInfo Controller", description="苹果账号信息")
public class AppleAccountInfoController {

	@Autowired
	private IAppleAccountInfoService iAppleAccountInfoService;

	/**
	 * @Author XIAOEN
	 * @Description 新增苹果账号信息
	 * @Date 2018/11/8 20:25
	 * @Param [appleAccountInfoVO]
	 * @return java.lang.Object
	 **/
	@PostMapping("/insertAppleAccountInfo")
	@ApiOperation(value = "新增苹果账号信息",notes = "新增苹果账号信息，appleAccountInfoVO")
	public Object insertAppleAccountInfo(@RequestBody @ApiParam(required = true) AppleAccountInfoVO appleAccountInfoVO){
		return iAppleAccountInfoService.insertAppleAccountInfo(appleAccountInfoVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 分页查询苹果账号信息
	 * @Date 2018/11/8 20:31
	 * @Param [appleAccountInfoVO, pageVO]
	 * @return java.lang.Object
	 **/
	@GetMapping("/findByAppleAccountInfoPage")
	@ApiOperation(value = "分页查询苹果账号信息",notes = "分页查询苹果账号信息，appleAccountInfoVO、pageVO")
	public Object findByAppleAccountInfoPage(AppleAccountInfoVO appleAccountInfoVO, PageVO pageVO){
		return iAppleAccountInfoService.findByAppleAccountInfoPage(appleAccountInfoVO, pageVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 获取苹果账号信息
	 * @Date 2018/11/8 12:43
	 * @Param [appleAccountInfoVO]
	 * @return java.util.List<com.common.model.AppleAccountInfoVO>
	 **/
	@GetMapping("/selectAppleAccountInfo")
	@ApiOperation(value = "获取苹果账号",notes = "获取苹果账号，条件：appleAccountInfoVO")
	public Object selectAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
		return iAppleAccountInfoService.selectAppleAccountInfo(appleAccountInfoVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 更新苹果账号信息
	 * @Date 2018/11/8 20:31
	 * @Param [appleAccountInfoVO]
	 * @return java.lang.Object
	 **/
	@PostMapping("/updateAppleAccountInfo")
	@ApiOperation(value = "更新苹果账号信息",notes = "更新苹果账号信息，appleAccountInfoVO")
	public Object updateAppleAccountInfo(@RequestBody AppleAccountInfoVO appleAccountInfoVO){
		return iAppleAccountInfoService.updateAppleAccountInfo(appleAccountInfoVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 删除苹果账号信息
	 * @Date 2018/11/8 12:43
	 * @Param [appleAccountInfoVO]
	 * @return int
	 **/
	@GetMapping("/deleteAppleAccountInfo")
	@ApiOperation(value = "删除苹果账号信息",notes = "删除苹果账号信息，appleAccountInfoIds数组")
	public Object deleteAppleAccountInfo(AppleAccountInfoVO appleAccountInfoVO){
		return iAppleAccountInfoService.deleteAppleAccountInfo(appleAccountInfoVO);
	}

}
