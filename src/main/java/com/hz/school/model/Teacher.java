package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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
     * 照片
     */
    private String iconpath;
    /**
     * 卡
     */
    @OneToMany(mappedBy = "teacher")
    private List<Card> cards;

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

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
