package com.ruoyi.service.entity;

import com.ruoyi.service.domain.DouUser;
import lombok.Data;

@Data
public class LoginDouUser {
	private String wxOpenid;
	private DouUser user;
}
