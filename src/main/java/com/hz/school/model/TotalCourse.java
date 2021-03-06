package com.hz.school.model;

import javax.persistence.*;
import java.util.List;

/**
 * 总行政班课程表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"total_course")
public class TotalCourse extends BaseEntity {
    /**
     * 学期
     */
    private String termName;
    /**
     * 班级
     */
    @ManyToOne
    private ClassInfo classInfo;
    /**
     * 当前课表的日期
     * yyyy-MM-dd转换的long类型
     */
    private Long date;
    /**
     * 周几
     */
    private Integer weekday;
    /**
     * 第几节课
     */
    private Integer classNum;
    /**
     * 时段
     * 1：上午；2：下午
     */
    @Column(length = 1)
    private Integer timeInterval;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程表
     */
    @ManyToOne
    private CourseInfo course;
    /**
     * 教师姓名
     */
    private String teacherName;
    /**
     * 教师表
     */
    @ManyToOne
    private Teacher teacher;
    /**
     * 周信息
     * 可以判断是第几周
     */
    private String weekInfo;

    /**
     * 班级类型
     * 1：走班，0：不是走班
     */
    @Column(length = 1)
    private Integer type;



    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(String weekInfo) {
        this.weekInfo = weekInfo;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
