/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 */
package cc.douyidou.web.controller.api;

import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.LoginResponse;
import cc.douyidou.service.entity.LoginMaDTO;
import cc.douyidou.service.service.WxUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserLoginApi {

	private final WxUserService wxUserService;
	/**
	 * 小程序用户登录
	 * @param request
	 * @param loginMaDTO
	 * @return
	 */
	@PostMapping("/miniLogin")
	public CommonResult<LoginResponse> login(HttpServletRequest request, @RequestBody LoginMaDTO loginMaDTO){
		 return wxUserService.miniLogin(loginMaDTO.getCode(),request);
	}
	@PostMapping("/refreshToken")
	public CommonResult<LoginResponse> refreshToken(HttpServletRequest request){
		return wxUserService.refreshToken(request);
	}

}
