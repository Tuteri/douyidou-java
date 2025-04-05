package com.ruoyi.service.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 支持的平台对象 dou_platform
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
@Data
public class DouPlatform extends DouBase
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 平台名 */
    @Excel(name = "平台名")
    private String name;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 可用状态 */
    @Excel(name = "可用状态")
    private Integer status;

    /** 权重 */
    @Excel(name = "权重")
    private Integer weight;
}
