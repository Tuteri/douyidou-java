package cc.douyidou.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import cc.douyidou.common.annotation.Excel;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class DouTranslate extends DouBase
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;
    
    /** 自定义任务名 */
    @Excel(name = "自定义任务名")
    private String name;
    
    /** 视频url */
    @Excel(name = "视频url")
    private String url;

    /** 原格式 */
    @Excel(name = "原格式")
    private String source;

    /** 现格式 */
    @Excel(name = "现格式")
    private String target;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 转码状态 */
    @Excel(name = "转码状态")
    private Integer status;

    /** 任务名 */
    @Excel(name = "任务名")
    private String task;
    
    /** 已转换文件大小 kb */
    @Excel(name = "文件大小")
    private BigInteger size;

    /** 下载地址 */
    @Excel(name = "下载地址")
    private String downloadUrl;
    
    /** 目标时长 */
    @Excel(name = "目标时长")
    private Integer targetTime;
    /** 类型 */
    @Excel(name = "类型")
    private Integer type;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date doneTime;
    
}
