package com.hz.school.resource;

import com.avaje.ebean.Ebean;
import com.hz.school.model.Account;
import com.hz.school.util.EbeanUtil;
import com.hz.school.util.Logger;
import com.hz.school.util.StringUtil;

/**
 * Created by Administrator on 2017/6/24.
 */
public class LoginResource {
    private static Logger log= Logger.getLogger(LoginResource.class);
    /**
     * 用户登录
     */
    public static String findAccount(String name,String password){
        if(StringUtil.isEmpty(name) || StringUtil.isEmpty(password))return "用户名或密码不能为空";
        Account account= EbeanUtil.find(Account.class).where().eq("name",name).setMaxRows(1).findUnique();
        return checkLogin(account,password);
    }
    private static String checkLogin(Account account,String password){
        Long lockTime=account.getLockTime();
        Long now=System.currentTimeMillis();
        Long longTime=(lockTime+1*60*60*3600)-now;
        if(lockTime==-1){
            return "临时锁定："+account.getLockReason();
        }
        if(lockTime!=0 && lockTime>=0){
            return "账号锁定，请于"+longTime/3600/60+"分钟后再试";
        }
        try{
            Ebean.beginTransaction();
            if(checkPassword(account,password)){
                //密码正确，登录成功
                account.setLockTime(0L);
                account.setFailNumber(0L);
                account.setLockReason(null);
                Ebean.save(account);
                Ebean.commitTransaction();
                return "success";
            }else{
                //密码错误，登录失败
                Long failNumber=account.getFailNumber();
                if(failNumber>=4){
                    //发送短信提醒

                    //错误登录次数太多，进行锁定
                    account.setFailNumber(failNumber+1);
                    account.setLockTime(System.currentTimeMillis());
                    account.setLockReason("错误登录次数太多");
                }
                account.setFailNumber(failNumber+1);
                Ebean.save(account);
                Ebean.commitTransaction();
                return "fail:";
            }
        }catch (Exception e){
            log.error("fail checkLogin",e);
            Ebean.rollbackTransaction();
            return "error:";
        }finally {
            Ebean.endTransaction();
        }


    }
    private static boolean checkPassword(Account account,String password){
        if(StringUtil.isEmpty(account.getSalt())){
            if(password.equals(account.getPassword())){
                return true;
            }else {
                return false;
            }
        }else{
            if(account.checkPassword(password)){
                return true;
            }else {
                return  false;
            }
        }
    }
}
