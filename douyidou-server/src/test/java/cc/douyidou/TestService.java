package cc.douyidou;

import cc.douyidou.service.service.IDouParseService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestService {
	@Resource
	IDouParseService douParseService;
	
	@Test
	void filterDownloadUrl(){
		douParseService.filterDownloadUrl();
	}
}
