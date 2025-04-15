package cc.douyidou.service.service;

import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.system.domain.SysConfig;
import cc.douyidou.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Service
public class DouConfigService {
	@Autowired
	ISysConfigService configService;
	public CommonResult<Map<String,Object>> getRoutine(){
		List<SysConfig> routine = configService.routine();
		Map<String, Object> map = new HashMap<>();
		routine.forEach(config -> {
			String name = config.getConfigKey().substring(8);
			if(!"app_id".equals(name) && !"app_secret".equals(name)){
				map.put(name, config.getConfigValue());
			}
		});
		return CommonResult.success(map);
	}
}
