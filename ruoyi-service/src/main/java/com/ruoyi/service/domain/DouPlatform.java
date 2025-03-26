package com.ruoyi.service.domain;

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
public class DouPlatform extends BaseEntity
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setWeight(Integer weight) 
    {
        this.weight = weight;
    }

    public Integer getWeight() 
    {
        return weight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("icon", getIcon())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("weight", getWeight())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
