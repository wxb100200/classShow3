package com.hz.school.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

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

    /**
     * 卡内余额
     */
    @Column(scale = 2)
    private BigDecimal balance=new BigDecimal(0.00);

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
