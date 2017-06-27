package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 学生考勤表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"student_attendance")
public class StudentAttendance extends BaseEntity{
    /**
     * 出勤次数
     */
    private Integer attCount;
    /**
     * 迟到次数
     */
    private Integer attLateCount;
    /**
     * 早退次数
     */
    private Integer attTardyCount;
    /**
     * 学生
     */
    @ManyToOne
    private Student student;
    /**
     * 学生出勤信息列表
     */
    @OneToMany(mappedBy = "studentAttendance")
    private List<StudentAttendanceDetail> attDetailList;

    public Integer getAttCount() {
        return attCount;
    }

    public void setAttCount(Integer attCount) {
        this.attCount = attCount;
    }

    public Integer getAttLateCount() {
        return attLateCount;
    }

    public void setAttLateCount(Integer attLateCount) {
        this.attLateCount = attLateCount;
    }

    public Integer getAttTardyCount() {
        return attTardyCount;
    }

    public void setAttTardyCount(Integer attTardyCount) {
        this.attTardyCount = attTardyCount;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<StudentAttendanceDetail> getAttDetailList() {
        return attDetailList;
    }

    public void setAttDetailList(List<StudentAttendanceDetail> attDetailList) {
        this.attDetailList = attDetailList;
    }
}
