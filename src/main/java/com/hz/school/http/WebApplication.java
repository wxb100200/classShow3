package com.hz.school.http;

import com.hz.school.http.common.Common;
import com.hz.school.http.common.CommonUtil;

/**
 * 
 * <p>Title:WebApplication</p>
 * <p>Description:Web应用</p>
 * @author rhwayfun
 * @date Sep 17, 2015 6:56:11 PM
 * @version 1.0
 */
public class WebApplication {
	/**
	 * 根据请求的url路径查找对应的Servlet
	 * @Description: TODO
	 */
	public static GenericServlet getServlet(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		Common common= CommonUtil.getCommon();
		GenericServlet servlet=null;
		if(common.getTotalCourse()){
			try {
				Thread.sleep( 5* 1000);
				getServlet(url);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("--->>>url:" + url);
			String clz="";
			if(clz.contains("classbrand_getClassCourseList")){
				clz="com.hz.school.http.servlet.GetClassCourseList";
			}else if(clz.contains("classbrand_getTakeClassStuList")){
				clz="com.hz.school.http.servlet.GetTakeClassStuList";
			}
			Class<?> clazz = Class.forName(clz);
			servlet = (GenericServlet) clazz.newInstance();
		}
		return servlet;
	}
}
