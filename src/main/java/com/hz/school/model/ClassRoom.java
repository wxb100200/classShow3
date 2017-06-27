package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 教室信息表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"class_room")
public class ClassRoom extends BaseEntity{
    /**
     * 教室名称
     * 如：202
     */
    private String classRoomName;

    /**
     * 班级
     */
    @OneToOne(mappedBy = "classRoom")
    private ClassInfo classInfo;

    public String getClassRoomName() {
        return classRoomName;
    }

    public void setClassRoomName(String classRoomName) {
        this.classRoomName = classRoomName;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}
