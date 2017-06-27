package com.hz.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 人员信息
 */
@Entity
@Table(name = Constant.DB_PREFIX+"person")
public class Person extends BaseEntity {
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     * 1、男，2、女
     */
    @Column(length = 1)
    private Integer sex;
    /**
     * 手机
     */
    private String phoneNum;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账号
     */
    @OneToMany(mappedBy = "person")
    private List<Account> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
