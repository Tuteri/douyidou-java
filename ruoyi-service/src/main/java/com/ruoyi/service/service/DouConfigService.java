package com.ruoyi.service.service;

import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DouConfigService {
	@Autowired
	ISysConfigService configService;
	public CommonResult<Map<String,Object>> getRoutine(){
		String notice = configService.selectConfigByKey("routine.notice");
		Map<String, Object> map = new HashMap<>();
		map.put("notice",notice);
		return CommonResult.success(map);
	}
}
