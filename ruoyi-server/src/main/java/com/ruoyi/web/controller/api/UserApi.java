package com.ruoyi.web.controller.api;

import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.user.DouUserResponse;
import com.ruoyi.service.domain.DouUser;
import com.ruoyi.service.service.IDouUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

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
