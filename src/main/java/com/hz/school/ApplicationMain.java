package com.hz.school;

import com.hz.school.quartz.TestQuartz;
import com.hz.school.util.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.text.ParseException;

/**
 * Created by Administrator on 2017/6/28.
 */
public class ApplicationMain {
    private static Logger log= Logger.getLogger(ApplicationMain.class);
    //通过SchedulerFactory来获取一个调度器
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    public static void main(String[] args){
        testQuartz();
    }

    private static void testQuartz(){
        Scheduler scheduler= null;
        try {
            scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail=new JobDetail("TestQuartz", "SYNCHRONY", TestQuartz.class);
            CronTrigger trigger=new CronTrigger("TestQuartzTrigger", "SYNCHRONY");
            trigger.setCronExpression("0 0 0/1 * * ?");
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
