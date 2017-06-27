package com.hz.school.gui.buttonHandler;


import com.hz.school.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/23.
 */
public class GoClassStudentCourseHandler implements ActionListener {
    private static Logger log= Logger.getLogger(GoClassStudentCourseHandler.class);

    private static ScheduledExecutorService service = null;
    private static Runnable runnable = new Runnable() {
        public void run() {
            log.info("--------->>>>>>GoClassStudentCourseHandler<<<<<<------------------------");
//            SynClassInfo.classbrand_getBzrClassid();
        }
    };

    public void actionPerformed(ActionEvent e) {
        JButton button=(JButton)e.getSource();
        String label=e.getActionCommand();
        if(label.contains("正在")){
            log.info("----->>>>>>暂停同步数据");
            button.setText("暂停同步数据。。");
            button.setBackground(Color.red);
            if(service!=null && !service.isShutdown()){
                service.shutdownNow();
                service=null;
            }
        }else{
            log.info("----->>>>>>开始同步数据");
            button.setText("正在同步数据。。");
            button.setBackground(Color.green);
            if(service==null){
                service = Executors.newSingleThreadScheduledExecutor();
                long oneDay = 24 * 60 * 60 * 1000;
                long initDelay  = getTimeMillis("01:00:00") - System.currentTimeMillis();
                initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, initDelay, oneDay, TimeUnit.MILLISECONDS);
            }
        }
    }
    /**
     * 获取指定时间对应的毫秒数
     * @param time "HH:mm:ss"
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
