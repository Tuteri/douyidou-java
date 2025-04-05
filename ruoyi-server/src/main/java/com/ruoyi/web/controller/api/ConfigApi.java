package com.ruoyi.web.controller.api;

import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.domain.DouHelp;
import com.ruoyi.service.service.DouConfigService;
import com.ruoyi.service.service.IDouHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigApi {
	
	@Autowired
	DouConfigService douConfigService;
	@Autowired
	IDouHelpService douHelpService;
	@GetMapping("/routine")
	public CommonResult<Map<String,Object>> getRoutine(){
		return douConfigService.getRoutine();
	}
	@GetMapping("/help")
	public CommonResult<List<DouHelp>> help(){
		return CommonResult.success(douHelpService.getList());
	}
}
