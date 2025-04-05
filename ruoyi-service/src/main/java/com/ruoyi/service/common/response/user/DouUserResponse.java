package com.ruoyi.service.common.response.user;

import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.service.domain.DouUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
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
	/** 用户等级*/
	private Integer level;
	/** 解析次数*/
	private Integer parseNum;
	/** tokens*/
	private Integer tokens;
	/** 解析次数临时*/
	private Integer parseNumTemp;
	/** 解析广告是否跳过*/
	private Boolean adSkipParse = false;
	/** 下载广告是否跳过*/
	private Boolean adSkipSave = false;
	
	public DouUserResponse(DouUser douUser) {
		BeanUtils.copyProperties(douUser,this);
	}
}
