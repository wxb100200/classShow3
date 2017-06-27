package com.hz.school.gui.buttonHandler;

import com.hz.school.quartz.TestQuartz;
import com.hz.school.util.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2017/6/23.
 */
public class TotalCourseHandlerQuartz implements ActionListener{
    private static Logger log= Logger.getLogger(TotalCourseHandlerQuartz.class);
    //通过SchedulerFactory来获取一个调度器
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static Scheduler scheduler;

    public void actionPerformed(ActionEvent e) {
        JButton button=(JButton)e.getSource();
        String label=e.getActionCommand();
        log.info("------>>>>>>>label:"+label);
        if(label.contains("正在")){                    //暂停
            button.setText("同步总课表数据。。");
            button.setBackground(Color.red);
            try {
                if(scheduler!=null && !scheduler.isShutdown()){
                    scheduler.shutdown();
                    scheduler=null;
                }
            } catch (SchedulerException e1) {
                e1.printStackTrace();
            }
        }else{                                          //开始
            button.setText("正在同步总课表数据。。");
            button.setBackground(Color.green);
            if(scheduler==null){
                try {
                    scheduler=schedulerFactory.getScheduler();
                    JobDetail jobDetail=new JobDetail("jobDetail-j1", "jobGroup-g1", TestQuartz.class);
                    CronTrigger trigger=new CronTrigger("cronTrigger", "triggerGroup-tg2");
                     /* 每半分钟执行一次  */
                    trigger.setCronExpression("10 * * * * ?");
                    scheduler.scheduleJob(jobDetail, trigger);
                    scheduler.start();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
