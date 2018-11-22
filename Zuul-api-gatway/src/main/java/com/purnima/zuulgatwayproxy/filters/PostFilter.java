package com.purnima.zuulgatwayproxy.filters;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PostFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
    	HttpServletResponse servletResponse = context.getResponse();
		servletResponse.addHeader("X-Sample", UUID.randomUUID().toString());
		servletResponse.addHeader("Purnima", "Patidar");
   System.out.println("Inside Response Filter");

    return null;
  }

}