package com.ruoyi.service.common.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.service.domain.DouUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class DouUserResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** 微信昵称 */
	private String wxName;
	
	/** 微信openid */
	private String wxOpenid;
	
	/** 用户名 */
	private String username;
	
	/** 头像 */
	private String avatar;
	
}
