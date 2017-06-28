package com.hz.school.api.classbrand_getClassParentMsgList2;

import com.hz.school.api.ApiEntity;
import com.hz.school.api.classbrand_getClassParentMsgList.ApiMsgPhoto;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
@Entity
public class ApiClassParentMessage extends ApiEntity {
    /**
     * 消息id
     */
    private Long msgid;
    /**
     * 信息模式
     */
    private Integer contenttype;
    /**
     * 内容
     */
    private String content;
    /**
     * 班级id
     */
    private Long classid;
    /***
     * 发布时间
     */
    private String publishdate;

    public Long getMsgid() {
        return msgid;
    }

    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }

    public Integer getContenttype() {
        return contenttype;
    }

    public void setContenttype(Integer contenttype) {
        this.contenttype = contenttype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getClassid() {
        return classid;
    }

    public void setClassid(Long classid) {
        this.classid = classid;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }
}
