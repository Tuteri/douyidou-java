package com.ruoyi.web.controller.api;

import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.service.DouConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigApi {
	
	@Autowired
	DouConfigService douConfigService;
	@GetMapping("/routine")
	public CommonResult<Map<String,Object>> getRoutine(){
		return douConfigService.getRoutine();
	}
}
