package com.hz.school.http.common;

/**
 * Created by Administrator on 2017/7/14.
 */
public class CommonUtil {
    private static Common common;
    static {
        common=new Common();
    }
    public static Common getCommon(){
        return common;
    }
}
