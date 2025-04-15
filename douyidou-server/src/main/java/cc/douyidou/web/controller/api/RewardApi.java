package cc.douyidou.web.controller.api;

import cn.hutool.core.util.ObjectUtil;
import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.domain.DouReward;
import cc.douyidou.service.service.IDouRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
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
