package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 学生考勤详情
 */
@Entity
@Table(name = Constant.DB_PREFIX+"student_att_detail")
public class StudentAttendanceDetail extends BaseEntity{
    /**
     * 出勤时间
     */
    private Long attTime;
    /**
     * 家长留言
     */
    private String parentWord;
    /**
     * 学生考勤
     */
    @ManyToOne
    private StudentAttendance studentAttendance;

    public Long getAttTime() {
        return attTime;
    }

    public void setAttTime(Long attTime) {
        this.attTime = attTime;
    }

    public String getParentWord() {
        return parentWord;
    }

    public void setParentWord(String parentWord) {
        this.parentWord = parentWord;
    }

    public StudentAttendance getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(StudentAttendance studentAttendance) {
        this.studentAttendance = studentAttendance;
    }
}
