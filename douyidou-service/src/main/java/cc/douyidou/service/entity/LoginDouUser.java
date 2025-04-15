package cc.douyidou.service.entity;

import cc.douyidou.service.domain.DouUser;
import lombok.Data;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class LoginDouUser {
	private String wxOpenid;
	private DouUser user;
}
