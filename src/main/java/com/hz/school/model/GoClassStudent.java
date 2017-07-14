package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 每一节课对应的走班学生
 */
@Entity
@Table(name = Constant.DB_PREFIX+"go_class_student")
public class GoClassStudent  extends BaseEntity {
    /**
     * 走班课表
     */
    @ManyToOne
    private GoClassCourse goClassCourse;
    /**
     * 学生
     */
    @ManyToOne
    private Student student;

    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生学号
     */
    private String stuNo;
    /**
     * 学生学籍辅号
     */
    private String stuNo1;

    public GoClassCourse getGoClassCourse() {
        return goClassCourse;
    }

    public void setGoClassCourse(GoClassCourse goClassCourse) {
        this.goClassCourse = goClassCourse;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuNo1() {
        return stuNo1;
    }

    public void setStuNo1(String stuNo1) {
        this.stuNo1 = stuNo1;
    }
}
