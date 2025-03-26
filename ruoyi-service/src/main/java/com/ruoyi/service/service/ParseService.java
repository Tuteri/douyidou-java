package com.ruoyi.service.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.service.api.ApiClient;
import com.ruoyi.service.common.request.PageParamRequest;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.parse.TranscodeResponse;
import com.ruoyi.service.common.response.parse.VideoParseResponse;
import com.ruoyi.service.domain.DouTranslate;
import com.ruoyi.service.exception.DouException;
import com.ruoyi.service.utils.DouUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.bouncycastle.oer.its.etsi102941.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ParseService {
	private final Logger logger = LoggerFactory.getLogger(ParseService.class);
	@Autowired
	ISysConfigService sysConfigService;
	@Autowired
	IDouTranslateService translateService;
	
	public CommonResult<VideoParseResponse> url(String url) {
		try {
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.make(url);
			if (!res.get("code").equals(200)) {
				logger.error("解析失败 ==> " + res);
				throw new DouException("解析失败");
			}
			//String jsonString = JSONObject.toJSONString(res.get("data"));
			JSONObject data = JSONObject.from(res.get("data"));
			VideoParseResponse videoParseResponse = data.toJavaObject(VideoParseResponse.class);
			return CommonResult.success(videoParseResponse);
		} catch (Exception e) {
			return CommonResult.failed("解析失败");
		}
	}
	
	public CommonResult<TranscodeResponse> transcode(DouTranslate translate) {
		try {
			String url = translate.getUrl();
			String ext = DouUtils.getUrlExt(url);
			if (!ext.equals(".m3u8")) {
				return CommonResult.failed("url仅支持m3u8视频格式");
			}
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.transcode(url);
			if (!res.get("code").equals(200)) {
				throw new DouException("转码失败");
			}
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			System.out.println(data);
			DouTranslate douTranslate = new DouTranslate();
			BeanUtils.copyProperties(transcodeResponse, douTranslate);
			douTranslate.setUrl(url);
			// 自定义任务名
			String name = StringUtils.isNotEmpty(translate.getName()) ? translate.getName() : douTranslate.getTask().replaceAll("-","");
			if(name.length()>10){
				name = name.substring(0,10);
			}
			douTranslate.setName(name);
			translateService.insertOne(douTranslate, transcodeResponse);
			BeanUtils.copyProperties(douTranslate,transcodeResponse);
			transcodeResponse.setStats(douTranslate.getStatus());
			return CommonResult.success(transcodeResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return CommonResult.failed("转码失败");
		}
	}
	
	/**
	 * 查询全部的任务状态
	 */
	public void transcodeStatsTask() {
		LambdaQueryWrapper<DouTranslate> lqw = new LambdaQueryWrapper<>();
		lqw.in(DouTranslate::getStatus, Arrays.asList(-1,0,1));
		lqw.orderByAsc(DouTranslate::getId);
		List<DouTranslate> list = translateService.list(lqw);
		for (DouTranslate douTranslate : list) {
			try {
				TranscodeResponse transcodeResponse = getTranscodeStats(douTranslate.getTask());
				Integer stats = transcodeResponse.getStats();
				
				switch (stats){
					// 等待中
					case 0:
						continue;
					// 转码中
					case 1:
						douTranslate.setSize(transcodeResponse.getSize());
						break;
					// 完成
					case 2:
						douTranslate.setSize(transcodeResponse.getSize());
						douTranslate.setDownloadUrl(transcodeResponse.getUrl());
					// 失败
					case 3:
					// 终止
					case 4:
						douTranslate.setDoneTime(new Date());
						break;
				}
				douTranslate.setStatus(stats);
				translateService.updateById(douTranslate);
				
			}catch (DouException e){
				e.printStackTrace();
			}
			
		}
	}
	public TranscodeResponse getTranscodeStats(String task) {
		try {
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.getTranscodeStats(task);
			if (!res.get("code").equals(200)) {
				throw new DouException("获取转码信息失败");
			}
			System.out.println(res);
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			return transcodeResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DouException("获取转码信息失败");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public CommonResult<TranscodeResponse> stopTranscode(String task) {
		try {
			DouTranslate douTranslate = translateService.findByTask(task);
			if (Objects.isNull(douTranslate)) {
				return CommonResult.failed("任务未找到");
			}
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.stopTranscode(task);
			if (!res.get("code").equals(200)) {
				throw new DouException("停止转码失败");
			}
			System.out.println(res);
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse translateResponse = data.toJavaObject(TranscodeResponse.class);
			DouTranslate douTranslate1 = new DouTranslate();
			douTranslate1.setId(douTranslate.getId());
			douTranslate1.setStatus(douTranslate.getStatus());
			translateService.updateById(douTranslate1);
			return CommonResult.success(translateResponse);
		} catch (Exception e) {
			return CommonResult.failed("停止转码失败");
		}
	}
	
	private ApiClient getApiClient() {
		String url = sysConfigService.selectConfigByKey("parse.url");
		String appId = sysConfigService.selectConfigByKey("parse.app_id");
		String appSecret = sysConfigService.selectConfigByKey("parse.app_secret");
		return new ApiClient(appId, appSecret, url);
	}
}
