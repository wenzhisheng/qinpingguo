package com.admin.appleappidid.controller;

import com.common.appleappidid.service.IAppleAppididService;
import com.common.model.AppleAppididVO;
import com.common.model.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appleAppidid")
@Api(value="AppleAppidid Controller", tags="AppleAppidid Controller", description="应用信息")
public class AppleAppididController {

    private final static Logger logger = LogManager.getLogger(AppleAppididController.class);

	@Autowired
	private IAppleAppididService iAppleAppididService;

	/**
	 * @Author XIAOEN
	 * @Description 新增应用信息
	 * @Date 2018/11/8 20:31
	 * @Param [appleAppididVO]
	 * @return java.lang.Object
	 **/
	@PostMapping("/insertAppleAppidid")
	@ApiOperation(value = "新增应用信息",notes = "新增应用信息，appleAppididVO")
	public Object insertAppleAppidid(@RequestBody @ApiParam(required = true) AppleAppididVO appleAppididVO){
		return iAppleAppididService.insertAppleAppidid(appleAppididVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 分页查询应用信息
	 * @Date 2018/11/8 20:31
	 * @Param [appleAppididVO, pageVO]
	 * @return java.lang.Object
	 **/
	@GetMapping("/findByAppleAppididPage")
	@ApiOperation(value = "分页查询应用信息",notes = "分页查询应用信息，appleAppididVO、pageVO")
	public Object findByAppleAppididPage(AppleAppididVO appleAppididVO, PageVO pageVO){
		return iAppleAppididService.findByAppleAppididPage(appleAppididVO, pageVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 列表查询应用信息
	 * @Date 2018/11/8 12:46
	 * @Param [appleAppididVO]
	 * @return java.util.List<com.common.model.AppleAppididVO>
	 **/
	@GetMapping("/listAppleAppidid")
	@ApiOperation(value = "列表查询应用信息",notes = "列表查询应用信息，appleAppididVO")
	public Object listAppleAppidid(AppleAppididVO appleAppididVO){
		return iAppleAppididService.listAppleAppidid(appleAppididVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 更新应用信息
	 * @Date 2018/11/8 20:32
	 * @Param [appleAppididVO]
	 * @return java.lang.Object
	 **/
	@PostMapping("/updateAppleAppidid")
	@ApiOperation(value = "更新应用信息",notes = "更新应用信息，appleAppididVO")
	public Object updateAppleAppidid(@RequestBody @ApiParam(required = true) AppleAppididVO appleAppididVO){
		return iAppleAppididService.updateAppleAppidid(appleAppididVO);
	}

	/**
	 * @Author XIAOEN
	 * @Description 删除应用信息
	 * @Date 2018/11/8 12:46
	 * @Param [appleAppididVO]
	 * @return int
	 **/
	@GetMapping("/deleteAppleAppidid")
	@ApiOperation(value = "删除应用信息",notes = "删除应用信息，appleAppididVO")
	public Object deleteAppleAppidid(AppleAppididVO appleAppididVO){
		return iAppleAppididService.deleteAppleAppidid(appleAppididVO);
	}

}
