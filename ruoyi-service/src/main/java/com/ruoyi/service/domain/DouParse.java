package com.ruoyi.service.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 视频解析记录对象 dou_parse
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
public class DouParse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    public void setUrlHash(String urlHash) 
    {
        this.urlHash = urlHash;
    }

    public String getUrlHash() 
    {
        return urlHash;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setVideo(String video) 
    {
        this.video = video;
    }

    public String getVideo() 
    {
        return video;
    }

    public void setAudio(String audio) 
    {
        this.audio = audio;
    }

    public String getAudio() 
    {
        return audio;
    }

    public void setImages(String images) 
    {
        this.images = images;
    }

    public String getImages() 
    {
        return images;
    }

    public void setCover(String cover) 
    {
        this.cover = cover;
    }

    public String getCover() 
    {
        return cover;
    }

    public void setText(String text) 
    {
        this.text = text;
    }

    public String getText() 
    {
        return text;
    }

    public void setPlatform(Integer platform) 
    {
        this.platform = platform;
    }

    public Integer getPlatform() 
    {
        return platform;
    }

    public void setProxy(String proxy) 
    {
        this.proxy = proxy;
    }

    public String getProxy() 
    {
        return proxy;
    }

    public void setOrigin(String origin) 
    {
        this.origin = origin;
    }

    public String getOrigin() 
    {
        return origin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("url", getUrl())
            .append("urlHash", getUrlHash())
            .append("type", getType())
            .append("video", getVideo())
            .append("audio", getAudio())
            .append("images", getImages())
            .append("cover", getCover())
            .append("text", getText())
            .append("platform", getPlatform())
            .append("proxy", getProxy())
            .append("origin", getOrigin())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
