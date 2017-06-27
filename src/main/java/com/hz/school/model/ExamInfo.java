package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 考试信息表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"exam_info")
public class ExamInfo extends BaseEntity {
    /**
     * 考试名称
     */
    private String examTitle;
    /**
     * 考试时间
     */
    private Long examTime;
    /**
     * 班级
     */
    @ManyToOne
    private ClassInfo classInfo;
    /**
     * 考试科目
     */
    @OneToMany(mappedBy = "examInfo")
    private List<ExamCourse> courseList;

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public Long getExamTime() {
        return examTime;
    }

    public void setExamTime(Long examTime) {
        this.examTime = examTime;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public List<ExamCourse> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<ExamCourse> courseList) {
        this.courseList = courseList;
    }
}
