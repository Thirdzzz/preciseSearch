package com.thtf.ezone.ajaxupload.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	public void destroy() {
		//Filter被销毁
		//System.out.println(this.getClass()+" destroy() was invoked.");

	}
    
	//request service
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		//解决中文化问题
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=gbk");
		
		//System.out.println("doFilter...");
		//不要遗漏调用拦截器链上的doFilter方法，把控制权交给其他资源
		//包括其他filter或者目标资源
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// 初始化
		//System.out.println(this.getClass()+" init(FilterConfig config) was invoked.");

	}

}
