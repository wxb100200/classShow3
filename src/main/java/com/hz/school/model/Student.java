package com.hz.school.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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
    /**
     * 当前使用的卡号
     */
    private String cardNo;

    /**
     * 排序号
     */
    @Column(name = "order1")
    private Integer order=0;
    /**
     * 性别
     * 1、男，2、女
     */
    @Column(length = 1)
    private Integer sex;
    /**
     * 民族
     */
    private String nation;
    /**
     * 民族代码
     */
    private Long  nationCode;
    /**
     * 身高
     */
    @Column(scale = 2)
    private BigDecimal height;
    /**
     * 政治面貌
     */
    private String political;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 学生头像
     */
    private String picpath;
    /**
     * 家长信息
     */
    @OneToMany(mappedBy = "student")
    private List<Parent> parentList;

    /**
     * 班级
     */
    @ManyToOne
    private ClassInfo classInfo;
    /**
     * 卡
     */
    @ManyToOne
    private Card card;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Long getNationCode() {
        return nationCode;
    }

    public void setNationCode(Long nationCode) {
        this.nationCode = nationCode;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
