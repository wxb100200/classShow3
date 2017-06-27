package com.hz.school.filter;



import com.avaje.ebean.Ebean;
import com.hz.school.model.Person;
import com.hz.school.util.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by wxb on 2016/11/14.
 */
public class CharEncodingFilter implements Filter {
    private static Logger log=Logger.getLogger(CharEncodingFilter.class);

    String encoding;
    public void init(FilterConfig cfg) throws ServletException {
        String e=cfg.getInitParameter("encoding");
        int num= Ebean.find(Person.class).findRowCount();
        System.out.println("------------>>>num:"+num);
        if(e==null||"".equals(e.trim())){
            encoding="UTF-8";
        }else {
            encoding=e;
        }
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("---------------1------------------------");
        System.out.println(request.toString());
        request.setCharacterEncoding(encoding);
        chain.doFilter(request,response);
        log.info("---------------2------------------------");
        System.out.println(response.toString());
    }

    public void destroy() {



    }
}
