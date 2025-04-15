package cc.douyidou.service.utils;

import cc.douyidou.service.entity.LoginDouUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public class SecurityUtils {
	public static LoginDouUser getLoginDouUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginDouUser loginDouUser = (LoginDouUser) authentication.getPrincipal();
		return loginDouUser;
	}
	public static Long getLoginDouUserId() {
		
		return getLoginDouUser().getUser().getId();
	}
}
