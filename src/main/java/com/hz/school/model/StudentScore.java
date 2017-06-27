package com.hz.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 学生考试分数
 */
@Entity
@Table(name = Constant.DB_PREFIX+"student_score")
public class StudentScore extends BaseEntity {
    /**
     * 科目
     */
    @ManyToOne
    private CourseInfo courseInfo;
    /**
     * 科目分数
     */
    @Column(scale = 1)
    private BigDecimal score;

    @ManyToOne
    private StudentGrade studentGrade;

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public StudentGrade getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(StudentGrade studentGrade) {
        this.studentGrade = studentGrade;
    }
}
