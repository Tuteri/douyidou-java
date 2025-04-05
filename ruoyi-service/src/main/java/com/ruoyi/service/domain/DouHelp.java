package com.ruoyi.service.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.service.domain.DouBase;
import lombok.Data;

/**
 * 使用说明对象 dou_help
 * 
 * @author Tuteri
 * @date 2025-04-05
 */
@Data
public class DouHelp extends DouBase
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String description;

    /** 展开 */
    @Excel(name = "展开")
    private Long expand;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;
    /** 排序 */
    @Excel(name = "排序")
    private Integer weight;

}
