package com.hz.school.resource;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.text.json.JsonContext;
import com.hz.school.api.ApiResult;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017-7-26.
 */
public class PageRecordResult {
    private static JsonContext jsonContext = Ebean.createJsonContext();
    private Integer total;
    private List<Object> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public static PageRecordResult single(Object obj){
        return list(Arrays.asList(new Object[]{obj}));
    }
    public static PageRecordResult list(List list){
        return multiple(list);
    }
    /*public static PageRecordResult error(String mee){
        return multiple();
    }
    public static PageRecordResult error(String api, String errorCode, Throwable exception){
        return multiple(false,api,null,0,errorCode,exception.getMessage()+"错误详情");
    }*/
    private static PageRecordResult multiple(List list){
        PageRecordResult result=new PageRecordResult();
        result.total=list.size();
        result.data=list;
        return result;
    }

    /*public  String toJson() {
        return "{\"total\":\""+total+"\",\"rows\":"+jsonContext.toJsonString(data)+"}";
    }*/

    public  String toJson() {
        return jsonContext.toJsonString(data);
    }
}
