package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 班委信息
 */
@Entity
@Table(name = Constant.DB_PREFIX+"committee")
public class Committee extends BaseEntity{
    /**
     * 班委代码
     */
    private String committeeCode;
    /**
     * 班委职务
     */
    private String committeeName;
    /**
     * 学生
     */
    @ManyToOne
    private Student student;
    /**
     * 班级信息表
     */
    @ManyToOne
    private ClassInfo classInfo;

    public String getCommitteeCode() {
        return committeeCode;
    }

    public void setCommitteeCode(String committeeCode) {
        this.committeeCode = committeeCode;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}
