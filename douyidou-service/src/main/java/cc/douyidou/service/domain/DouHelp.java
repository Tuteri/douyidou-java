package cc.douyidou.service.domain;

import cc.douyidou.common.annotation.Excel;
import lombok.Data;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
