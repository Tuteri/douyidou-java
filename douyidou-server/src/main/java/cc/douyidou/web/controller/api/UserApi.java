package cc.douyidou.web.controller.api;

import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.user.DouUserResponse;
import cc.douyidou.service.domain.DouUser;
import cc.douyidou.service.service.IDouUserService;
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
@RequestMapping("/api")
public class UserApi {
	@Autowired
	IDouUserService douUserService;
	
	@GetMapping("getInfo")
	public CommonResult<DouUserResponse> getInfo() {
		return CommonResult.success(douUserService.getInfo());
	}
	
	@GetMapping("user/info")
	public CommonResult<DouUserResponse> userInfo() {
		return CommonResult.success(douUserService.getInfo());
	}
	
	//@GetMapping("user/limits")
	//public CommonResult<DouUserResponse> userInfo() {
	//	return CommonResult.success(douUserService.getInfo());
	//}
	
	/**
	 * 用户消费解析次数
	 * @return
	 */
	@GetMapping("user/consumer")
	public CommonResult<String> userInfo(@RequestParam(value = "type",defaultValue = "1") Integer type) {
		douUserService.parseNumConsumer(type);
		return CommonResult.success();
	}
	/**
	 * 用户兑换解析次数
	 * @return
	 */
	@PostMapping("user/tokensToParseNum")
	public CommonResult<Object> tokensToParseNum(@RequestBody DouUser douUserRequest) {
		return douUserService.tokensToParseNum(douUserRequest);
	}
}
