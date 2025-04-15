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
public class DouReward extends DouBase
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 广告id */
    @Excel(name = "广告id")
    private String adId;

    /** 解析次数 */
    @Excel(name = "解析次数")
    private Integer parseNum;

    /** tokens */
    @Excel(name = "tokens")
    private Integer tokens;

    /** 奖励来源 */
    @Excel(name = "奖励来源")
    private String source;

    /** 类型 0未知 1解析 2下载 3转码 4转码下载 */
    @Excel(name = "类型 0未知 1解析 2下载 3转码 4转码下载")
    private Integer type;
    /** 是否为主动 */
    @Excel(name = "是否为主动")
    private Integer proactive;
    
}
