package cc.douyidou.service.common.response.parse;

import com.alibaba.fastjson2.JSONObject;
import cc.douyidou.common.utils.bean.BeanUtils;
import cc.douyidou.service.domain.DouParse;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class VideoParseResponse implements Serializable {
	private String type;      // 类型（video、audio等）
	private List<String> video = new ArrayList<>();     // 视频链接
	private List<String> images = new ArrayList<>();;    // 图片地址
	private List<String> audio = new ArrayList<>();;     // 音频地址
	private String music;     // 音乐地址
	private String cover;     // 封面图片
	private String text;      // 文本内容
	private Integer platform;  // 平台信息
	private Map<String,Object> proxy;
	private Map<String,Object> origin;
	
	public VideoParseResponse() {
	}
	
	public VideoParseResponse(DouParse douParse) {
		BeanUtils.copyProperties(douParse,this);
		this.setVideo(JSONObject.parseObject(douParse.getVideo(),List.class));
		this.setImages(JSONObject.parseObject(douParse.getImages(),List.class));
		this.setAudio(JSONObject.parseObject(douParse.getAudio(),List.class));
		this.setProxy(JSONObject.parseObject(douParse.getProxy()));
		this.setOrigin(JSONObject.parseObject(douParse.getOrigin()));
	}
}