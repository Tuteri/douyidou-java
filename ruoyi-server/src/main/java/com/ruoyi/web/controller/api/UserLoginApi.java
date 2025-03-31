/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 */
package com.ruoyi.web.controller.api;

import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.LoginResponse;
import com.ruoyi.service.entity.LoginMaDTO;
import com.ruoyi.service.service.WxUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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
