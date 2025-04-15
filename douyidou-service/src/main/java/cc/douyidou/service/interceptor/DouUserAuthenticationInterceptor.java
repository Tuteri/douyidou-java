package cc.douyidou.service.interceptor;

import cc.douyidou.common.exception.ServiceException;
import cc.douyidou.common.utils.StringUtils;
import cc.douyidou.service.entity.LoginDouUser;
import cc.douyidou.service.service.DouUserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Slf4j
@Component
public class DouUserAuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private DouUserTokenService tokenService;
	
	public DouUserAuthenticationInterceptor() {
	}
	
	public DouUserAuthenticationInterceptor(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		LoginDouUser loginDouUser = tokenService.getLoginUser(request);
		if (StringUtils.isNotNull(loginDouUser))
		{
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDouUser, null, null);
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			return true;
		}else{
			throw new ServiceException("认证失败", 401);
		}
	}
}
