package com.hz.school.quartz;

import com.hz.school.util.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 测试定时
 */
public class TestQuartz implements Job {
    private static Logger log= Logger.getLogger(TestQuartz.class);
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("--------------------开始测试定时任务--------------------");
//        run();
        log.info("--------------------结束测试定时任务--------------------");
    }
    private static void run(){
        log.info("----->>>>run <<<<-------------");
    }
}
