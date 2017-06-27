package com.hz.school.gui;

import com.hz.school.gui.buttonHandler.GoClassCourseHandler;
import com.hz.school.gui.buttonHandler.GoClassStudentCourseHandler;
import com.hz.school.gui.buttonHandler.TotalCourseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 2017/6/27.
 */
public class CourseMain {
    public static void main(String[] args){
        showView();
    }
    private static void showView(){
        JFrame jFrame=new JFrame("学军中学课表");

        JMenuBar jMenuBar=new JMenuBar();
        JMenu menu_port=new JMenu("接口信息");
        jMenuBar.add(menu_port);
        jFrame.setJMenuBar(jMenuBar);

        JLabel jLabel=new JLabel("欢迎使用",SwingUtilities.CENTER);
        Font font = new Font("宋体",Font.BOLD,24);
        jLabel.setFont(font);
        jLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jFrame.add(jLabel,BorderLayout.NORTH);

        portInfo(jFrame);


        jFrame.setSize(600, 400);
        jFrame.setLocation(300, 200);
        jFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        jFrame.setVisible(true);
    }
    private static void portInfo(JFrame jFrame){
        Font font = new Font("宋体",Font.PLAIN,20);
        Container container=new Container();
        container.setLayout(new GridLayout(3,2));

        JButton jb_totalCourse=insertOneRow(container,font,"同步总课表数据(每秒钟执行一次)");
        jb_totalCourse.addActionListener(new TotalCourseHandler());
        JButton jb_goClassCourse=insertOneRow(container,font,"同步班级走班课表数据(每半分钟执行一次)");
        jb_goClassCourse.addActionListener(new GoClassCourseHandler());
        JButton jb_goClassStudentCourse=insertOneRow(container,font,"同步学生走班课表数据(每天1:00:00执行一次)");
        jb_goClassStudentCourse.addActionListener(new GoClassStudentCourseHandler());

        jFrame.add(container,BorderLayout.CENTER);
    }
    private static JButton insertOneRow(Container container,Font font,String labelText){
        JPanel jPanel=new JPanel();
        JLabel jLabel = new JLabel(labelText,SwingUtilities.LEFT);
        jLabel.setFont(font);
        JButton jButton=new JButton("开始");
        jPanel.add(jLabel);
        jPanel.add(jButton);
        container.add(jPanel);
        return jButton;
    }
}
