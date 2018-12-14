package com.purnima.zuulgatwayproxy.filters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.purnima.zuulgatwayproxy.model.Configurations;
import com.purnima.zuulgatwayproxy.repo.ConfigRepo;

public class PreFilter extends ZuulFilter {
	
	@Autowired
	private ConfigRepo config;

  @Override
  public String filterType() {
    return "pre";
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
    RequestContext ctx = RequestContext.getCurrentContext();
    ctx.addZuulRequestHeader("Purnima", "Test");
    HttpServletRequest request = ctx.getRequest();

    System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString() +"request >>");
    InputStream in = (InputStream) ctx.get("requestEntity");
    try {
    if (in == null) {
		
			in = ctx.getRequest().getInputStream();
		
	}
	String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
	System.out.println(body+"   >>>>>");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    List<Configurations> configEntity=config.findByPArtnerAndApiName("p1", "createApplication");
    System.out.println("sasdc");

    return "hi";
  }

}