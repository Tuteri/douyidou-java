package cc.douyidou.service.task;

import cc.douyidou.service.service.IDouRewardService;
import cc.douyidou.service.service.IDouUserService;
import cc.douyidou.service.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DouUserTask {
	private static final Logger log = LoggerFactory.getLogger(DouUserTask.class);
	
	@Autowired
	IDouUserService douUserService;
	
	/**
	 * 检查过期的临时次数
	 */
	public void clearPntTask(){
		douUserService.clearPntTask();
	}
}
