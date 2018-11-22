package com.example.springbootzuulgatwayproxystudentservice;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootZuulgatwayproxyStudentServiceApplication {

	@RequestMapping(value = "/echoEmpName/{name}")
	public String echoStudentName(@PathVariable(name = "name") String name,HttpServletRequest req, HttpServletResponse res) {
		System.out.println(req.getHeader("Purnima"));
		
		return "Hello  " + name + " Responsed on : " + new Date();
	}

	@RequestMapping(value = "/getEmpDetails/{name}")
	public Student getStudentDetails(@PathVariable(name = "name") String name) {
		return new Student(name, "Gurgaon", "Software En");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootZuulgatwayproxyStudentServiceApplication.class, args);
	}
}

class Student {
	String name;
	String address;
	String post;

	public Student(String name, String address, String cls) {
		super();
		this.name = name;
		this.address = address;
		this.post = cls;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPost() {
		return post;
	}

}
