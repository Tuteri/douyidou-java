package cc.douyidou.service.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import cc.douyidou.common.core.domain.BaseEntity;

import java.util.Map;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public class DouBase extends BaseEntity {
	@TableField(exist = false)
	private String searchValue;
	@TableField(exist = false)
	private String createBy;
	@TableField(exist = false)
	private String updateBy;
	@TableField(exist = false)
	private String remark;
	@TableField(exist = false)
	private Map<String, Object> params;
}
