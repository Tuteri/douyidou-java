package com.ruoyi.service.common.response.parse;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.service.domain.DouParse;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class DouParseResponse implements Serializable {
	private Long id;
	private String type;      // 类型（video、audio等）
	private String url;      // 原url
	private List<String> video = new ArrayList<>();     // 视频链接
	private List<String> images = new ArrayList<>();;    // 图片地址
	private List<String> audio = new ArrayList<>();;     // 音频地址
	private String cover;     // 封面图片
	private String text;      // 文本内容
	private Integer platform;  // 平台信息
	private Map<String,Object> proxy;
	private Map<String,Object> origin;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	public DouParseResponse() {
	}
	
	public DouParseResponse(DouParse douParse) {
		BeanUtils.copyProperties(douParse,this);
		this.setVideo(JSONObject.parseObject(douParse.getVideo(),List.class));
		this.setImages(JSONObject.parseObject(douParse.getImages(),List.class));
		this.setAudio(JSONObject.parseObject(douParse.getAudio(),List.class));
		this.setProxy(JSONObject.parseObject(douParse.getProxy()));
		this.setOrigin(JSONObject.parseObject(douParse.getOrigin()));
	}
}