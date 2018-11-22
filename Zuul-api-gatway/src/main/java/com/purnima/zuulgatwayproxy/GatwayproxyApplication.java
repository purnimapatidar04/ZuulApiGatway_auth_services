package com.purnima.zuulgatwayproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.purnima.zuulgatwayproxy.filters.ErrorFilter;
import com.purnima.zuulgatwayproxy.filters.PostFilter;
import com.purnima.zuulgatwayproxy.filters.PreFilter;
import com.purnima.zuulgatwayproxy.filters.RouteFilter;

@SpringBootApplication
@EnableZuulProxy
public class GatwayproxyApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(GatwayproxyApplication.class, args);
	}

//	@Bean
//	public PreFilter preFilter() {
//		System.out.println("PRe");
//		return new PreFilter();
//	}
//	@Bean
//	public PostFilter postFilter() {
//		System.out.println("post");
//		return new PostFilter();
//	}
//	@Bean
//	public ErrorFilter errorFilter() {
//		return new ErrorFilter();
//	}
//	@Bean
//	public RouteFilter routeFilter() {
//		System.out.println("rout");
//		return new RouteFilter();
//	}
}
