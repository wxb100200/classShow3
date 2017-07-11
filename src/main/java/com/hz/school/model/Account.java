package com.hz.school.model;

import cn.hillwind.common.util.BASE64Codec;
import com.hz.school.util.RandomUtil;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.security.MessageDigest;

/**
 * 账号信息表
 */
@Entity
@Table(name =Constant.DB_PREFIX+"account")
public class Account extends BaseEntity{
    /**
     *登录账号的用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码加密的盐
     */
    private String salt;
    /**
     * 锁定时间
     * 当登录失败次数超过5次，设置锁定时间；
     * 每次登录成功，将锁定时间设为0，登录失败次数设为0.
     * 当为-1时表示临时锁定，需要注明锁定原因
     */
    private Long lockTime=0L;
    /**
     * 锁定原因
     */
    private String lockReason;
    /**
     * 登录失败次数
     */
    private Long failNumber=0L;
    /**
     * 人员信息
     */
    @ManyToOne
    private Person person;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public Long getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(Long failNumber) {
        this.failNumber = failNumber;
    }
    /**
     * @param newPassword 新密码
     * @return true - 重置成功, false - 重置失败.
     */
    public boolean resetPassword(String newPassword){
        try{
            MessageDigest md5=MessageDigest.getInstance("MD5");
            this.salt = RandomUtil.generateRandomPassword(20); // 重置密码时，需要重新生产salt.
//            this.password =new String( BASE64Codec.encode(md5.digest((this.salt + newPassword).getBytes("utf-8"))) );
            return true;
        }catch (Exception exp){
            log.error("Error in reset password , new password:" + newPassword + ", salt: " + this.salt ,exp);
        }
        return false;
    }

    /**
     * 判断密码是否正确
     * @param tryPassword 用户输入的密码
     * @return true - 密码正确, false - 密码错误.
     */
    public boolean checkPassword(String tryPassword){
        try{
            MessageDigest md5=MessageDigest.getInstance("MD5");
//            return this.password.equals( new String( BASE64Codec.encode(md5.digest((this.salt + tryPassword).getBytes("utf-8"))) ) );
        }catch (Exception exp){
            log.error("Error in check password , new password:" + tryPassword + ", salt: " + this.salt ,exp);
        }
        return false;
    }

}
