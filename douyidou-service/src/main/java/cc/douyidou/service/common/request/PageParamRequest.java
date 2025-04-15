package cc.douyidou.service.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class PageParamRequest {
	
	@Schema(description = "页码", example = "1", minimum = "1", defaultValue = "1")
	private int page = 1;
	
	@Schema(description = "每页数量", example = "20", minimum = "1", maximum = "50", defaultValue = "20")
	private int limit = 20;
	
}
