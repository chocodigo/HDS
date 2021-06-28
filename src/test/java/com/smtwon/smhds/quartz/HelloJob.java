package com.smtwon.smhds.quartz;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob extends BaseJob{

	
	private static final Logger log = LoggerFactory.getLogger(HelloJob.class);

	@Override
	protected void doExecute(JobExecutionContext context) {

		log.info("### {} is being executed!", context.getJobDetail().getJobDataMap().get("JobName").toString());
		
	}
}
