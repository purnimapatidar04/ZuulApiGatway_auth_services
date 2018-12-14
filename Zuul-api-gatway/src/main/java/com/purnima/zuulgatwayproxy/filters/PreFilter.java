package com.purnima.zuulgatwayproxy.filters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.purnima.zuulgatwayproxy.dtos.ApplicationDetailsVO;
import com.purnima.zuulgatwayproxy.model.Configurations;
import com.purnima.zuulgatwayproxy.repo.ConfigRepo;
import com.purnima.zuulgatwayproxy.validators.PartnerValidator;

public class PreFilter extends ZuulFilter {
	
	@Autowired
	private ConfigRepo config;
	
	@Autowired
	private PartnerValidator partnerValidator;

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
	Gson g = new Gson();
	ApplicationDetailsVO am= g.fromJson(body, ApplicationDetailsVO.class);
	
	Errors er=new BeanPropertyBindingResult(am, am.getClass().getName());;
	Configurations configEntity=config.findByPArtnerAndApiName("p1", "createApplication");
    am.setRequiredFields(configEntity.fieldsForValidation());
    System.out.println("sasdc");
	partnerValidator.validate(am, er);
	if(er.hasErrors()) {
		ctx.setResponseBody((er.getFieldErrors().get(0).getCode())+"");
		ctx.setSendZuulResponse(false); 
	}
	
	System.out.println(body+"   >>>>>");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    

    return "hi";
  }

}