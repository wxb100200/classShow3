package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 学校发布的消息
 */
@Entity
@Table(name =Constant.DB_PREFIX+"message_school")
public class MessageSchool extends BaseEntity{
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息
     */
    @ManyToOne
    private Message message;
    /**
     * 班级
     */
    @ManyToOne
    private ClassInfo classInfo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
