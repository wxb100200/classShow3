package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 家长与学生的消息通信
 */
@Entity
@Table(name =Constant.DB_PREFIX+"message_parent")
public class MessageParent extends BaseEntity{
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

    /**
     * 照片
     */
    @OneToMany(mappedBy = "message")
    private List<Photo> photoList;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
