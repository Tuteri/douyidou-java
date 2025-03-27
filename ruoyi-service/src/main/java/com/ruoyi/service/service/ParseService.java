package com.ruoyi.service.service;

import cn.hutool.core.util.ObjectUtil;
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
import com.ruoyi.service.common.response.parse.DouParseResponse;
import com.ruoyi.service.common.response.parse.VideoParseResponse;
import com.ruoyi.service.domain.DouParse;
import com.ruoyi.service.domain.DouTranslate;
import com.ruoyi.service.exception.DouException;
import com.ruoyi.service.utils.DouUtils;
import com.ruoyi.service.utils.SecurityUtils;
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
	@Autowired
	IDouParseService douParseService;
	public CommonResult<DouParseResponse> url(String url) {
		try {
			String hash = DouUtils.md5(url);
			//DouParse findDouParse = douParseService.findByHash(hash);
			DouParse douParse = new DouParse();
			douParse.setUrl(url);
			douParse.setUrlHash(hash);
			douParse.setUid(SecurityUtils.getLoginDouUser().getUser().getId());
			douParse.setPlatform(0);
			douParse.setCreateTime(new Date());
			douParse.setUpdateTime(new Date());
			//if(ObjectUtil.isNotNull(findDouParse)){
			//	BeanUtils.copyProperties(findDouParse,douParse);
			//	douParse.setId(null);
			//	douParseService.save(douParse);
			//	DouParseResponse douParseResponse = new DouParseResponse(douParse);
			//	return CommonResult.success(douParseResponse);
			//}
			

			douParseService.save(douParse);
			Long id = douParse.getId();
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.make(url);
			if (!res.get("code").equals(200)) {
				logger.error("解析失败 ==> " + res);
				throw new DouException("解析失败");
			}
			//String jsonString = JSONObject.toJSONString(res.get("data"));
			JSONObject data = JSONObject.from(res.get("data"));
			DouParseResponse videoParseResponse = data.toJavaObject(DouParseResponse.class);
			BeanUtils.copyProperties(videoParseResponse,douParse);
			douParse.setPlatform(videoParseResponse.getPlatform());
			douParse.setVideo(JSONObject.toJSONString(videoParseResponse.getVideo()));
			douParse.setAudio(JSONObject.toJSONString(videoParseResponse.getAudio()));
			douParse.setImages(JSONObject.toJSONString(videoParseResponse.getImages()));
			douParse.setProxy(JSONObject.toJSONString(videoParseResponse.getProxy()));
			douParse.setOrigin(JSONObject.toJSONString(videoParseResponse.getOrigin()));
			douParse.setUpdateTime(new Date());
			douParse.setId(id);
			douParseService.updateById(douParse);
			DouParseResponse douParseResponse = new DouParseResponse(douParse);
			return CommonResult.success(douParseResponse);
		} catch (Exception e) {
			e.printStackTrace();
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
				throw new DouException("任务失败");
			}
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			System.out.println(data);
			DouTranslate douTranslate = new DouTranslate();
			douTranslate.setUrl(url);
			douTranslate.setSource(ext);
			douTranslate.setTarget("mp4");
			douTranslate.setType(1);
			
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
			return CommonResult.failed("任务失败");
		}
	}
	public CommonResult<TranscodeResponse> videoMd5(DouTranslate translate) {
		try {
			String url = translate.getUrl();
			String ext = DouUtils.getUrlExt(url);
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.videoMd5(url);
			if (!res.get("code").equals(200)) {
				throw new DouException("任务失败");
			}
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			System.out.println(data);
			DouTranslate douTranslate = new DouTranslate();
			douTranslate.setUrl(url);
			douTranslate.setSource(ext);
			douTranslate.setTarget("mp4");
			douTranslate.setType(2);
			
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
			return CommonResult.failed("任务失败");
		}
	}
	public CommonResult<TranscodeResponse> videoToMp3(DouTranslate translate) {
		try {
			String url = translate.getUrl();
			String ext = DouUtils.getUrlExt(url);
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.videoToMp3(url);
			if (!res.get("code").equals(200)) {
				throw new DouException("任务失败");
			}
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			System.out.println(data);
			DouTranslate douTranslate = new DouTranslate();
			douTranslate.setUrl(url);
			douTranslate.setSource(ext);
			douTranslate.setTarget("mp3");
			douTranslate.setType(3);
			
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
			return CommonResult.failed("任务失败");
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
				TranscodeResponse transcodeResponse;
				if(douTranslate.getType() == 1){
					transcodeResponse = getTranscodeStats(douTranslate.getTask());
				}else if(douTranslate.getType() == 2){
					transcodeResponse = getVideoMd5Stats(douTranslate.getTask());
				}else if(douTranslate.getType() == 3){
					transcodeResponse = getVideoToMp3Stats(douTranslate.getTask());
				}else {
					break;
				}
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
				douTranslate.setTargetTime(transcodeResponse.getTime());
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
				throw new DouException("获取任务信息失败");
			}
			System.out.println(res);
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			return transcodeResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DouException("获取任务信息失败");
		}
	}
	public TranscodeResponse getVideoMd5Stats(String task) {
		try {
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.getVideoMd5Stats(task);
			if (!res.get("code").equals(200)) {
				throw new DouException("获取任务信息失败");
			}
			System.out.println(res);
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			return transcodeResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DouException("获取任务信息失败");
		}
	}
	public TranscodeResponse getVideoToMp3Stats(String task) {
		try {
			ApiClient apiClient = getApiClient();
			Map<String, Object> res = apiClient.getVideoToMp3Stats(task);
			if (!res.get("code").equals(200)) {
				throw new DouException("获取任务信息失败");
			}
			System.out.println(res);
			JSONObject data = JSONObject.from(res.get("data"));
			TranscodeResponse transcodeResponse = data.toJavaObject(TranscodeResponse.class);
			return transcodeResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DouException("获取任务信息失败");
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
				throw new DouException("停止任务失败");
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
			return CommonResult.failed("停止任务失败");
		}
	}
	
	private ApiClient getApiClient() {
		String url = sysConfigService.selectConfigByKey("parse.url");
		String appId = sysConfigService.selectConfigByKey("parse.app_id");
		String appSecret = sysConfigService.selectConfigByKey("parse.app_secret");
		return new ApiClient(appId, appSecret, url);
	}
}
