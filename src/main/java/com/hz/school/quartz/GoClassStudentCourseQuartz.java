package com.hz.school.quartz;

import com.hz.school.util.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Administrator on 2017/6/29.
 */
public class GoClassStudentCourseQuartz implements Job {
    private static Logger log= Logger.getLogger(GoClassStudentCourseQuartz.class);
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("--------------------开始走班学生课表定时任务--------------------");
//        run();
        log.info("--------------------结束走班学生课表定时任务--------------------");
    }
}
