package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 班级圈
 */
@Entity
@Table(name =Constant.DB_PREFIX+"class_ring")
public class ClassRing extends BaseEntity{
    /**
     * 圈说明
     */
    private String content;
    /**
     * 点赞数
     */
    private Integer pariseCount;
    /**
     * 班级
     */
    private ClassInfo classInfo;
    /**
     * 照片
     */
    private List<Photo> photos;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPariseCount() {
        return pariseCount;
    }

    public void setPariseCount(Integer pariseCount) {
        this.pariseCount = pariseCount;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
