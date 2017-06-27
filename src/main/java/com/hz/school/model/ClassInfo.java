package com.hz.school.model;

import javax.persistence.*;
import java.util.List;

/**
 * 班级信息表
 */
@Entity
@Table(name =Constant.DB_PREFIX+"class_info")
public class ClassInfo extends BaseEntity{

    private Long campusid;
    /**
     * 班级口号
     */
    private String classInfo;
    /**
     * 班级名称
     * 如：高一1班
     */
    private String className;
    /**
     * 教室
     */
    @OneToOne
    private ClassRoom classRoom;
    /**
     * 班主任
     */
    @ManyToOne
    private Teacher headmaster;
    /**
     * 班级时间
     */
    @OneToMany(mappedBy = "classInfo")
    private List<ClassTime> classTimeList;
    /**
     * 班委信息
     */
    @OneToMany(mappedBy = "classInfo")
    private List<Committee> committeeList;

    @ManyToOne
    private Xqbm xqbm;

    public Long getCampusid() {
        return campusid;
    }

    public void setCampusid(Long campusid) {
        this.campusid = campusid;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Teacher getHeadmaster() {
        return headmaster;
    }

    public void setHeadmaster(Teacher headmaster) {
        this.headmaster = headmaster;
    }

    public List<ClassTime> getClassTimeList() {
        return classTimeList;
    }

    public void setClassTimeList(List<ClassTime> classTimeList) {
        this.classTimeList = classTimeList;
    }

    public List<Committee> getCommitteeList() {
        return committeeList;
    }

    public void setCommitteeList(List<Committee> committeeList) {
        this.committeeList = committeeList;
    }

    public Xqbm getXqbm() {
        return xqbm;
    }

    public void setXqbm(Xqbm xqbm) {
        this.xqbm = xqbm;
    }
}
