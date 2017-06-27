package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 班级信息表
 */
@Entity
@Table(name =Constant.DB_PREFIX+"student")
public class Student extends BaseEntity{
    /**
     * 姓名
     */
    private String name;
    /**
     * 学号
     */
    private String stuNo;

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
}
