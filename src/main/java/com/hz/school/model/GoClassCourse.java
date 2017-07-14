package com.hz.school.model;

import javax.persistence.*;
import java.util.List;

/**
 * 走班课表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"go_class_course")
public class GoClassCourse  extends BaseEntity {
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
     * 关联的学生
     */
    @OneToMany(mappedBy = "goClassCourse")
    private List<GoClassStudent> goClassStudents;

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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
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

    public List<GoClassStudent> getGoClassStudents() {
        return goClassStudents;
    }

    public void setGoClassStudents(List<GoClassStudent> goClassStudents) {
        this.goClassStudents = goClassStudents;
    }
}
