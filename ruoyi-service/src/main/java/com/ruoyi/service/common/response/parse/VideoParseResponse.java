package com.ruoyi.service.common.response.parse;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class VideoParseResponse implements Serializable {
	private String type;      // 类型（video、audio等）
	private List<String> video = new ArrayList<>();     // 视频链接
	private List<String> images = new ArrayList<>();;    // 图片地址
	private List<String> audio = new ArrayList<>();;     // 音频地址
	private String music;     // 音乐地址
	private String cover;     // 封面图片
	private String text;      // 文本内容
	private String platform;  // 平台信息
	private Map<String,Object> proxy;
	private Map<String,Object> origin;
}