package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 学生留言给家长
 */
@Entity
@Table(name =Constant.DB_PREFIX+"message_student")
public class MessageStudent extends BaseEntity{
    /**
     * 学生
     */
    @ManyToOne
    private Student student;
    /**
     * 消息
     */
    @ManyToOne
    private Message message;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
