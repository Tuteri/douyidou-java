package com.ruoyi.web.controller.api;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.service.common.response.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/api/common")
public class CommonApi {
	private static final Logger log = LoggerFactory.getLogger(CommonApi.class);
	
	@Autowired
	private ServerConfig serverConfig;
	
	/**
	 * 通用上传请求（单个）
	 */
	@PostMapping("/upload")
	public CommonResult<Object> uploadFile(MultipartFile file){
		try {
			// 上传文件路径
			String filePath = RuoYiConfig.getUploadPath();
			// 上传并返回新文件名称
			String fileName = FileUploadUtils.upload(filePath, file);
			String url = serverConfig.getUrl() + fileName;
			var map = new HashMap<>();
			map.put("url", url);
			map.put("fileName", fileName);
			map.put("newFileName", FileUtils.getName(fileName));
			map.put("originalFilename", file.getOriginalFilename());
			return CommonResult.success(map);
		} catch (Exception e) {
			return CommonResult.failed("文件上传失败");
		}
	}
}
