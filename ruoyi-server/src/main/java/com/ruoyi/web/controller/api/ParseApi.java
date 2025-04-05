package com.ruoyi.web.controller.api;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.service.common.PageResult;
import com.ruoyi.service.common.request.PageParamRequest;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.parse.DouParseResponse;
import com.ruoyi.service.common.response.parse.TranscodeResponse;
import com.ruoyi.service.domain.DouParse;
import com.ruoyi.service.domain.DouTranslate;
import com.ruoyi.service.exception.DouException;
import com.ruoyi.service.service.IDouParseService;
import com.ruoyi.service.service.IDouTranslateService;
import com.ruoyi.service.service.ParseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parse")
public class ParseApi {
	
	@Autowired
	ParseService parseService;
	@Autowired
	IDouTranslateService translateService;
	@Autowired
	IDouParseService douParseService;
	@GetMapping("url")
	public CommonResult<DouParseResponse> url(@RequestParam String url, HttpServletRequest request){
		if(StringUtils.isNull(url) || StringUtils.isEmpty(url) || StringUtils.isBlank(url)){
			throw new DouException("URL不能为空");
		}
		return parseService.make(url,request);
	}
	@GetMapping("url/list")
	public CommonResult<PageResult<DouParseResponse>> urlList(DouParse douParse, PageParamRequest pageParamRequest){
		return CommonResult.success(douParseService.getListByUser(douParse,pageParamRequest));
	}
	@GetMapping("url/info")
	public CommonResult<DouParseResponse> urlInfo(DouParse douParse){
		return douParseService.getInfo(douParse);
	}
	@PostMapping("transcode")
	public CommonResult<TranscodeResponse> transcode(@RequestBody DouTranslate translate){
		String url = translate.getUrl();
		if(StringUtils.isNull(url) || StringUtils.isEmpty(url) || StringUtils.isBlank(url)){
			throw new DouException("URL不能为空");
		}
		return parseService.transcode(translate);
	}
	@PostMapping("videoMd5")
	public CommonResult<TranscodeResponse> videoMd5(@RequestBody DouTranslate translate){
		String url = translate.getUrl();
		if(StringUtils.isNull(url) || StringUtils.isEmpty(url) || StringUtils.isBlank(url)){
			throw new DouException("URL不能为空");
		}
		return parseService.videoMd5(translate);
	}
	@PostMapping("videoToMp3")
	public CommonResult<TranscodeResponse> videoToMp3(@RequestBody DouTranslate translate){
		String url = translate.getUrl();
		if(StringUtils.isNull(url) || StringUtils.isEmpty(url) || StringUtils.isBlank(url)){
			throw new DouException("URL不能为空");
		}
		return parseService.videoToMp3(translate);
	}
	//@GetMapping("transcode/stats")
	//public CommonResult<TranscodeResponse> transcodeStats(@RequestParam String task){
	//	if(StringUtils.isNull(task) || StringUtils.isEmpty(task) || StringUtils.isBlank(task)){
	//		throw new DouException("task不能为空");
	//	}
	//	return parseService.getTranscodeStats(task);
	//}
	@GetMapping("transcode/stats")
	public CommonResult<TranscodeResponse> transcodeStats(@RequestParam Long id){
		if(ObjectUtil.isNull(id)){
			throw new DouException("id不能为空");
		}
		return translateService.getInfo(id);
	}
	@GetMapping("transcode/list")
	public CommonResult<PageResult<TranscodeResponse>> transcodeList(DouTranslate douTranslateRequest,PageParamRequest pageParamRequest){
		return CommonResult.success(translateService.getListByUser(douTranslateRequest,pageParamRequest));
	}
	@GetMapping("transcode/stop")
	public CommonResult<TranscodeResponse> transcodeStop(@RequestParam String task){
		if(StringUtils.isNull(task) || StringUtils.isEmpty(task) || StringUtils.isBlank(task)){
			throw new DouException("task不能为空");
		}
		return parseService.stopTranscode(task);
	}
}
