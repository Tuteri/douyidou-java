package com.ruoyi.web.controller.api;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.domain.DouReward;
import com.ruoyi.service.service.IDouRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reward")
public class RewardApi {
	
	@Autowired
	private IDouRewardService douRewardService;
	
	
	/**
	 * 广告结束 领奖
	 * @param douRewardRequest 请求
	 * @return CommonResult
	 */
	@PostMapping("claim")
	public CommonResult<Boolean> claim(@RequestBody DouReward douRewardRequest) {
		DouReward douReward = douRewardService.sendReward(douRewardRequest);
		return CommonResult.success(ObjectUtil.isNotNull(douReward));
	}
}
