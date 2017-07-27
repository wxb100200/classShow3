package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 班级班牌
 */
@Entity
@Table(name =Constant.DB_PREFIX+"class_board")
public class ClassBoard extends BaseEntity{
    /**
     * 班牌名称
     */
    private String name;
    /**
     * 班牌IP
     */
    private String ip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
