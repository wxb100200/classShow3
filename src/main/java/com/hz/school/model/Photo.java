package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 图片
 */
@Entity
@Table(name =Constant.DB_PREFIX+"photo")
public class Photo extends BaseEntity{
    /**
     * 照片url
     */
    private String url;

    /**
     * 消息
     */
    @ManyToOne
    private MessageParent message;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MessageParent getMessage() {
        return message;
    }

    public void setMessage(MessageParent message) {
        this.message = message;
    }
}
