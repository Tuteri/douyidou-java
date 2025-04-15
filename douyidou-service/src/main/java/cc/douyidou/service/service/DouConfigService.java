package cc.douyidou.service.service;

import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.system.domain.SysConfig;
import cc.douyidou.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
