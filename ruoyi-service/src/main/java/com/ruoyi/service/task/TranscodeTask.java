package com.ruoyi.service.task;

import com.ruoyi.service.service.DouUserTokenService;
import com.ruoyi.service.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
