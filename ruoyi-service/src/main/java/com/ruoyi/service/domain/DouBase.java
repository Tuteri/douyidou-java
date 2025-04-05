package com.ruoyi.service.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Map;

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
