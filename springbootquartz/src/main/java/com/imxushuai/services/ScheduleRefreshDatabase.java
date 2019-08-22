package com.imxushuai.services;

import com.imxushuai.entities.mysql.JobCron;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
 
@Slf4j
@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshDatabase {
	@Autowired
	private JobCronService jobCronService;

	@Resource(name = "jobDetail")
	private JobDetail jobDetail;
 
	@Resource(name = "jobTrigger")
	private CronTrigger cronTrigger;
 
	@Resource(name = "scheduler")
	private Scheduler scheduler;

	/**
	 * 每隔5s查询数据库，并根据查询结果决定是否重新设置定时任务
	 */
	@Scheduled(fixedRate = 5000)
	public void scheduleUpdateCronTrigger() throws SchedulerException {
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
		// 当前Trigger使用的cron表达式
		String currentCron = trigger.getCronExpression();
		// 从数据库查询当前启用的cron表达式
		JobCron cron = jobCronService.findByCurrent("1");
		// 从数据库查询出来的
		String searchCron = cron.getCron();
		if (currentCron.equals(searchCron)) {
			// 如果当前使用的cron表达式和从数据库中查询出来的cron表达式一致，则不刷新任务
		} else {
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
			// 按新的cronExpression表达式重新构建trigger
			trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
			trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())
					.withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
			log.info("任务执行时间间隔被修改：[{}]", cron.getDescription());
		}
	}
}
