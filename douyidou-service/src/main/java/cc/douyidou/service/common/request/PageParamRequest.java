package cc.douyidou.service.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 分页请求类
@Data
public class PageParamRequest {
	
	@Schema(description = "页码", example = "1", minimum = "1", defaultValue = "1")
	private int page = 1;
	
	@Schema(description = "每页数量", example = "20", minimum = "1", maximum = "50", defaultValue = "20")
	private int limit = 20;
	
}
