package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学期信息表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"term_info")
public class TermInfo extends BaseEntity {
    /**
     * 学期名
     */
    private String termName;
    /**
     * 学段
     */
    private String termStage;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermStage() {
        return termStage;
    }

    public void setTermStage(String termStage) {
        this.termStage = termStage;
    }
}
