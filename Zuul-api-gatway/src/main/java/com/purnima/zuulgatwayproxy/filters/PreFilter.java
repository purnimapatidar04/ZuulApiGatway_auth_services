package com.purnima.zuulgatwayproxy.filters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.purnima.zuulgatwayproxy.dtos.ApplicationDetailsVO;
import com.purnima.zuulgatwayproxy.model.ClientRequest;
import com.purnima.zuulgatwayproxy.model.Configurations;
import com.purnima.zuulgatwayproxy.model.UpdateBusinessRequest;
import com.purnima.zuulgatwayproxy.repo.ConfigRepo;
import com.purnima.zuulgatwayproxy.util.Utility;
import com.purnima.zuulgatwayproxy.validators.PartnerValidator;

import lombok.Data;

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
    HttpServletRequest request = ctx.getRequest();
    System.out.println("header >>"+request.getHeader("Authorization"));
    System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString() +"request >>");
    InputStream in = (InputStream) ctx.get("requestEntity");
    try {
    if (in == null) {
		
			in = ctx.getRequest().getInputStream();
		
	}
    
    String url=request.getRequestURL().toString().split("/")[request.getRequestURL().toString().split("/").length-1];
	System.out.println(url);
	
	if(url.equals("getApplicationData")) {
		ctx.setResponseBody("Application "+request.getHeader("applicationNumber")+" is eligible for loan amount : 1000000");
		ctx.setSendZuulResponse(false); 
		return null;
	}
	
    String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
    System.out.println(body+"   >>>>>");
	Gson g = new Gson();
	ClientRequest req= g.fromJson(body, ClientRequest.class);
	final ApplicationDetailsVO applicationDetailVo;
	if(url.equals("updateBusiness")) {
		UpdateBusinessRequest businessReq=Utility.extractObject(req.getData(), UpdateBusinessRequest.class);
		 applicationDetailVo=Utility.extractObject(businessReq.getData(), ApplicationDetailsVO.class);
	}
	else {
		applicationDetailVo = Utility.extractObject(req.getData(), ApplicationDetailsVO.class);
	}
	   Errors er=new BeanPropertyBindingResult(applicationDetailVo, applicationDetailVo.getClass().getName());
		Configurations configEntity=config.findByPArtnerAndApiName(request.getHeader("Authorization"), url);
		applicationDetailVo.setRequiredFields(configEntity.fieldsForValidation());
	    System.out.println("sasdc");
		partnerValidator.validate(applicationDetailVo, er);
		String s="";
		int i=0;
		if(er.hasErrors()) {
			for (FieldError fe : er.getFieldErrors()) {
				s+=er.getFieldErrors().get(i).getCode()+"\n";
				i++;
			
			}
			ctx.setResponseBody(s);
			ctx.setSendZuulResponse(false); 
		}
	//}
	 
	
	System.out.println(body+"   >>>>>");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    

    return "hi";
  }
  
  @Data
  class LogInWrapper{
		String pin;
		String mobile;
  }

}