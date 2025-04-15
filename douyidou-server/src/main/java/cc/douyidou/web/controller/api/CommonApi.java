package cc.douyidou.web.controller.api;

import cc.douyidou.common.config.RuoYiConfig;
import cc.douyidou.common.utils.file.FileUploadUtils;
import cc.douyidou.common.utils.file.FileUtils;
import cc.douyidou.framework.config.ServerConfig;
import cc.douyidou.service.common.response.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
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
