package com.hz.school.quartz;

import com.hz.school.http.common.Common;
import com.hz.school.http.common.CommonUtil;
import com.hz.school.synchrony.GetCourseStudent;
import com.hz.school.util.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Administrator on 2017/6/29.
 */
public class GoClassCourseQuartz implements Job {
    private static Logger log= Logger.getLogger(GoClassCourseQuartz.class);
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("--------------------开始走班课表定时任务--------------------");
        Common common= CommonUtil.getCommon();
        common.goClassCourseTrue();
        GetCourseStudent.requestUrl();
        common.goClassCourseFalse();
        log.info("--------------------结束走班课表定时任务--------------------");
    }
}
