package cc.douyidou.service.domain;

import lombok.Data;
import cc.douyidou.common.annotation.Excel;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
