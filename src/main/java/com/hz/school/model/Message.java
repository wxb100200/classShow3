package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 消息
 */
@Entity
@Table(name =Constant.DB_PREFIX+"message_student")
public class Message extends BaseEntity{
    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     * 1、语音，2、文字
     */
    private Integer contentType;
    /**
     * 发布日期
     */
    private Long publishdate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Long getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Long publishdate) {
        this.publishdate = publishdate;
    }
}
