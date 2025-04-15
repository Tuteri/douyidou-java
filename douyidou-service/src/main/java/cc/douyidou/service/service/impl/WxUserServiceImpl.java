/*
MIT License

Copyright (c) 2020 www.joolun.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package cc.douyidou.service.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.util.ObjectUtil;
import cc.douyidou.common.core.redis.RedisCache;
import cc.douyidou.common.exception.ServiceException;
import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.LoginResponse;
import cc.douyidou.service.domain.DouUser;
import cc.douyidou.service.service.DouUserTokenService;
import cc.douyidou.service.service.IDouUserService;
import cc.douyidou.service.service.WxUserService;
import cc.douyidou.service.utils.DouUtils;
import cc.douyidou.system.service.ISysConfigService;
import cc.douyidou.service.entity.LoginDouUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Slf4j
@Service
public class WxUserServiceImpl implements WxUserService {
	
	@Autowired
	private RedisCache redisCache;
	@Autowired
	ISysConfigService sysConfigService;
	@Autowired
	DouUserTokenService userTokenService;
	@Autowired
	IDouUserService douUserService;
	
	private long lastTimestamp = -1L;
	private int counter = 0;
	
	//@Override
	//public WxUser getByOpenId(String openId) {
	//	return this.baseMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
	//			.eq(WxUser::getOpenId, openId));
	//}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public CommonResult<LoginResponse> miniLogin(String code,HttpServletRequest request) {
		try {
			WxMaJscode2SessionResult jscode2session = getWxService().jsCode2SessionInfo(code);
			if (ObjectUtil.isNull(jscode2session)) {
				throw new ServiceException("登录失败", 400);
			}
			DouUser douUser = douUserService.findByWxOpenid(jscode2session.getOpenid());
			if (douUser == null) {
				//新增微信用户
				douUser = new DouUser();
				douUser.setWxName("微信用户_" + (new Random().nextInt(9000) + 1000));
				douUser.setWxOpenid(jscode2session.getOpenid());
				String username = generate();
				douUser.setUsername(username);
				douUser.setPassword(md5(username)); // 使用 MD5 加密
				douUser.setCreateTime(new Date());
				douUser.setUpdateTime(new Date());
				var ip = DouUtils.getClientIp(request);
				douUser.setRegisterIp(ip);
				douUser.setLoginIp(ip);
				//douUser.setSessionKey(jscode2session.getSessionKey());
				//douUser.setUnionId(jscode2session.getUnionid());
				douUserService.save(douUser);
			} else {
				var updateDouUser = new DouUser();
				updateDouUser.setLastLoginIp(douUser.getLoginIp());
				updateDouUser.setLoginIp(DouUtils.getClientIp(request));
				updateDouUser.setUpdateTime(new Date());
				updateDouUser.setId(douUser.getId());
				//更新SessionKey
				douUserService.updateById(updateDouUser);
			}
			LoginDouUser loginDouUser = new LoginDouUser();
			loginDouUser.setWxOpenid(douUser.getWxOpenid());
			return createUserToken(loginDouUser);
			
		} catch (WxErrorException e) {
			throw new ServiceException("登录失败", 400);
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public CommonResult<LoginResponse> refreshToken(HttpServletRequest request) {
		LoginDouUser refreshLoginUser = userTokenService.getRefreshLoginUser(request);
		if (ObjectUtil.isNull(refreshLoginUser)) {
			throw new ServiceException("认证失败", 401);
		}
		String wxOpenid = refreshLoginUser.getWxOpenid();
		

		//移除旧的token
		redisCache.deleteObject(wxOpenid + "_rt");
		redisCache.deleteObject(wxOpenid + "_at");
		return createUserToken(refreshLoginUser);
		
	}
	
	@NotNull
	private CommonResult<LoginResponse> createUserToken(LoginDouUser loginUser) {
		String token = this.userTokenService.createToken(loginUser);
		String refreshToken = this.userTokenService.createRefreshToken(loginUser);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken(token);
		loginResponse.setRefreshToken(refreshToken);
		loginResponse.setExpire(this.userTokenService.getExpireTime());
		return CommonResult.success(loginResponse);
	}
	
	public WxMaService getWxService() {
		String appId = sysConfigService.selectConfigByKey("routine.app_id");
		String appSecret = sysConfigService.selectConfigByKey("routine.app_secret");
		WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
		config.setAppid(appId);
		config.setSecret(appSecret);
		WxMaService service = new WxMaServiceImpl();
		service.setWxMaConfig(config);
		return service;
	}
	
	public String generate() {
		long timestamp = Instant.now().toEpochMilli(); // 获取当前毫秒时间戳
		
		// 如果时间戳相同，增加计数器
		if (timestamp == lastTimestamp) {
			counter++;
		} else {
			counter = 0;
			lastTimestamp = timestamp;
		}
		
		// 获取时间戳的最后 6 位
		String tsPart = String.valueOf(timestamp).substring(String.valueOf(timestamp).length() - 6);
		
		// 数学变换（前 3 位 *2，后 3 位 *3）
		int prefix = Integer.parseInt(tsPart.substring(0, 3)) * 2;
		int suffix = Integer.parseInt(tsPart.substring(3, 6)) * 3;
		
		// 组合并确保 8 位
		String username = String.format("%08d", Integer.parseInt(prefix + "" + suffix + counter));
		
		return username.length() > 8 ? username.substring(0, 8) : username;
	}
	
	// MD5 加密
	public String md5(String input) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(input.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : array) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("MD5 加密失败", e);
		}
	}
	
}
