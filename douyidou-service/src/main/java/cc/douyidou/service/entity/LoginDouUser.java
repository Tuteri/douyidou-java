package cc.douyidou.service.entity;

import cc.douyidou.service.domain.DouUser;
import lombok.Data;

@Data
public class LoginDouUser {
	private String wxOpenid;
	private DouUser user;
}
