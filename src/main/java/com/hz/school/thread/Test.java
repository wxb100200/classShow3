package com.hz.school.thread;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Test {
    public static void main(String[] args){
        Common common1=CommonUtil.getCommon();
        System.out.println("common1 totalCourse:"+common1.getTotalCourse());
        Common common2=CommonUtil.getCommon();
        common2.totalCourseTrue();
        Common common3=CommonUtil.getCommon();
        System.out.println("common3 totalCourse:"+common3.getTotalCourse());
        Common common4=CommonUtil.getCommon();
        System.out.println(common1==common2);
        System.out.println(common1.equals(common2));
    }
}
