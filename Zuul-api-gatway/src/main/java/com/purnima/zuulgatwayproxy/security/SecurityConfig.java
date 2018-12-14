//package com.purnima.zuulgatwayproxy.security;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import main.java.com.security.JwtAuthenticationConfig;
//import main.java.com.security.JwtTokenAuthenticationFilter;
//
///**
// * Config role-based auth.
// *
// * @author purnima 2017/10/18
// */
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private JwtAuthenticationConfig config;
//
//	@Bean
//	public JwtAuthenticationConfig jwtConfig() {
//		return new JwtAuthenticationConfig();
//	}
//
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		System.out.println("api gatway1");
//		httpSecurity.csrf().disable().logout().disable().formLogin().disable().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().anonymous().and().exceptionHandling()
//				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
//                    .addFilterAfter(new JwtTokenAuthenticationFilter(config),
//                            UsernamePasswordAuthenticationFilter.class)
//				.authorizeRequests().antMatchers(config.getUrl()).permitAll()
//
//				.antMatchers("/emp/echoEmpName/*").hasRole("ADMIN").antMatchers("/backend/user").hasRole("USER")
//				.antMatchers("/student/echoStudentName/*").hasRole("USER");
//
//	}
//}
