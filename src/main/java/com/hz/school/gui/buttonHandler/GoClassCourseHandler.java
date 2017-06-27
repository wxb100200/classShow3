package com.hz.school.gui.buttonHandler;


import com.hz.school.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/23.
 */
public class GoClassCourseHandler implements ActionListener {
    private static Logger log= Logger.getLogger(GoClassCourseHandler.class);

    private static ScheduledExecutorService service = null;
    private static Runnable runnable = new Runnable() {
        public void run() {
            log.info("--------->>>>>>GoClassCourseHandler<<<<<<------------------------");
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
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 5, 30, TimeUnit.MILLISECONDS);
            }
        }
    }
}
