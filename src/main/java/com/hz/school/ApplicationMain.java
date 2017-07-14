package com.hz.school;

import com.hz.school.util.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by Administrator on 2017/6/28.
 */
public class ApplicationMain {
    private static Logger log= Logger.getLogger(ApplicationMain.class);
    //通过SchedulerFactory来获取一个调度器
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    public static void main(String[] args){
        Scheduler scheduler= null;
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
