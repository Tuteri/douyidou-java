package cc.douyidou.service.utils;

import cc.douyidou.service.entity.LoginDouUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


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
