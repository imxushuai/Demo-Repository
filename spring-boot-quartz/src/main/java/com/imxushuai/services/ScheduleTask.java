package com.imxushuai.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@Component
@EnableScheduling
public class ScheduleTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);


	/**
	 * 测试任务
	 */
	public void sayHello() {
		LOGGER.info("测试定时任务！");
	}

}


