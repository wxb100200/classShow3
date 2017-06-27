package com.hz.school.model;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 卡信息表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"card")
public class Card extends BaseEntity{
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡类型
     * * 1学生卡、2班主任卡、3其他老师卡
     */
    private Integer type;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
