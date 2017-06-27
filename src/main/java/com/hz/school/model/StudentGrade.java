package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 学生考试成绩表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"student_grade")
public class StudentGrade extends BaseEntity {
    /**
     * 考试信息
     */
    @ManyToOne
    private ExamInfo examInfo;
    /**
     * 单次考试，班级内该学生总分排名
     */
    private Integer ranking;
    /**
     * 老师的评语
     */
    private String teacherComment;

    @ManyToOne
    private Student student;

    @OneToMany(mappedBy = "studentGrade")
    private List<StudentScore> scoreList;

    public ExamInfo getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<StudentScore> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<StudentScore> scoreList) {
        this.scoreList = scoreList;
    }
}
