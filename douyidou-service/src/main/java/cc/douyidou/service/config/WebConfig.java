package cc.douyidou.service.config;

import cc.douyidou.service.interceptor.DouUserAuthenticationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
