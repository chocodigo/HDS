package com.smtown.smhds.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <pre>
 *	BaseJob.java - Job 추상 클래스
 * </pre>
 *
 * @author	방재훈
 * @since	2019.09.05
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.09.05	방재훈		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public abstract class BaseJob implements Job{

	private static final Logger log = LoggerFactory.getLogger(BaseJob.class);
	
    /*
     * execute - Job Chaining 시 Main ㅡㄷㅅ
     * @param JobExecutionContext
     * @return
     * @exception
     */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
        //beforeExecute(context);
        doExecute(context);
        //afterExecute(context);
        //scheduleNextJob(context);
		
	}
	
    /*
     * SelectADUserJob Main Method
     * @param JobExecutionContext
     * @return
     * @exception
     */
	private void beforeExecute(JobExecutionContext context) {
		log.info("### Before executing job");
	}
	
    /*
     * SelectADUserJob Main Method
     * @param JobExecutionContext
     * @return
     * @exception
     */
	public abstract void doExecute(JobExecutionContext context);
	
    /*
     * SelectADUserJob Main Method
     * @param JobExecutionContext
     * @return
     * @exception
     */
	private void afterExecute(JobExecutionContext context) {
		log.info("### After executing job");
	}
	
    /*
     * SelectADUserJob Main Method
     * @param JobExecutionContext
     * @return
     * @exception
     */
	private void scheduleNextJob(JobExecutionContext context) {
		log.info("### Schedule Next job");
	}
}