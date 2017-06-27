package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 老师信息表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"teacher")
public class Teacher extends BaseEntity{
    /**
     * 姓名
     */
    private String name;
    /**
     * 职称
     * 老师、班主任
     */
    private String named;

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

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
