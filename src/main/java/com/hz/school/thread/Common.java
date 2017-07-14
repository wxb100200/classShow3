package com.hz.school.thread;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Common {
    private boolean totalCourse=false;

    public synchronized boolean getTotalCourse(){
        return totalCourse;
    }
    public synchronized void totalCourseTrue(){
        this.totalCourse=true;
    }
    public synchronized void totalCourseFalse(){
        this.totalCourse=false;
    }
}
