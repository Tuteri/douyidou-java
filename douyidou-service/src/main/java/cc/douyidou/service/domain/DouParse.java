package cc.douyidou.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import cc.douyidou.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class DouParse extends DouBase
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;
    /** uid */
    private Long uid;

    /** 视频url */
    @Excel(name = "视频url")
    private String url;

    /** hash md5 */
    @Excel(name = "hash md5")
    private String urlHash;

    /** 视频类型 video|note|slides */
    @Excel(name = "视频类型 video|note|slides")
    private String type;

    /** 视频地址 */
    @Excel(name = "视频地址")
    private String video;

    /** 音频地址 */
    @Excel(name = "音频地址")
    private String audio;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String images;

    /** 封面地址 */
    @Excel(name = "封面地址")
    private String cover;

    /** 文案 */
    @Excel(name = "文案")
    private String text;

    /** 平台 0未知 */
    @Excel(name = "平台 0未知")
    private Integer platform;

    /** 代理 json */
    private String proxy;

    /** 源 json */
    private String origin;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;
    
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    
}
