package cc.douyidou.service.task;

import cc.douyidou.service.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Component
public class TranscodeTask {
	private static final Logger log = LoggerFactory.getLogger(TranscodeTask.class);
	@Autowired
	ParseService parseService;
	public void stats(){
		log.info("TranscodeTask::stats 定时任务");
		parseService.transcodeStatsTask();
	}
}
