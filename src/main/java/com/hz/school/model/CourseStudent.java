package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 总课表中走班课表对应的学生
 */
@Entity
@Table(name = Constant.DB_PREFIX+"course_student")
public class CourseStudent extends BaseEntity {
    /**
     * 学期
     */
    private String termName;
    /**
     * 教室名称
     */
    private String classRoomName;
    /**
     * 教室
     */
    @ManyToOne
    private ClassRoom classRoom;
    /**
     * 日期
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

    /**
     * 学生
     */
    @ManyToOne
    private Student student;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

    public void setClassRoomName(String classRoomName) {
        this.classRoomName = classRoomName;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
