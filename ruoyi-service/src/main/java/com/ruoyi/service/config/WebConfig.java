package com.ruoyi.service.config;

import com.ruoyi.service.interceptor.DouUserAuthenticationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final RedisTemplate redisTemplate;
	@Bean
	public HandlerInterceptor getDouUserAuthenticationInterceptor(){
		return new DouUserAuthenticationInterceptor();
	}
	/**
	 * 拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 进入ThirdSession拦截器
		 */
		registry.addInterceptor(getDouUserAuthenticationInterceptor())
				.addPathPatterns("/api/**")//拦截/api/**接口
				.excludePathPatterns("/api/miniLogin","/api/refreshToken","/api/f/**");//放行接口
	}
}
