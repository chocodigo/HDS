package com.smtwon.smhds.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseJob implements Job{

	
	private static final Logger log = LoggerFactory.getLogger(BaseJob.class);

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
        beforeExecute(context);
        doExecute(context);
        afterExecute(context);
        scheduleNextJob(context);
		
	}
	
	private void beforeExecute(JobExecutionContext context) {
		log.info("### Before executing job");
	}
	
	protected abstract void doExecute(JobExecutionContext context);
	
	private void afterExecute(JobExecutionContext context) {
		log.info("### After executing job");
	}
	
	private void scheduleNextJob(JobExecutionContext context) {
		log.info("### Schedule Next job");
	}
}