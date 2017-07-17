package com.hz.school.http.common;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Common {
    private boolean totalCourse=false;
    private boolean goClassCourse=false;

    public synchronized boolean getTotalCourse(){
        return totalCourse;
    }
    public synchronized void totalCourseTrue(){
        this.totalCourse=true;
    }
    public synchronized void totalCourseFalse(){
        this.totalCourse=false;
    }

    public synchronized boolean getGoClassCourse(){
        return goClassCourse;
    }
    public synchronized void goClassCourseTrue(){
        this.goClassCourse=true;
    }
    public synchronized void goClassCourseFalse(){
        this.goClassCourse=false;
    }
}
