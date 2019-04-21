package com.me.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control","no-cache,no-store,max-age=0,s-maxage=0,must-revalidate,proxy-revalidate,private,max-stale=0,post-check=0");
			response.setDateHeader("Expires", 0L);

			chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
