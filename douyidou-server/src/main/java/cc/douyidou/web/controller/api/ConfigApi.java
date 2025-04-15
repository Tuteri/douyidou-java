package cc.douyidou.web.controller.api;

import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.domain.DouHelp;
import cc.douyidou.service.service.DouConfigService;
import cc.douyidou.service.service.IDouHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
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
