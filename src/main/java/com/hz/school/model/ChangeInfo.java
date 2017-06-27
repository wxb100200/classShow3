package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 数据变动通知接口
 */
@Entity
@Table(name = Constant.DB_PREFIX+"changeInfo")
public class ChangeInfo extends BaseEntity{
    /**
     * 学校代码
     */
    private String campuscode;
    /**
     * 接口方法
     */
    private String apiMethod;
    /**
     * 微校通侧班级id,32位字符串
     */
    @ManyToOne
    private ClassInfo classInfo;

    public String getCampuscode() {
        return campuscode;
    }

    public void setCampuscode(String campuscode) {
        this.campuscode = campuscode;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}
