package com.javasree.spring.familytree;


import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationHibernate {
	
	/*@Bean(name="sessionFactory")
	public HibernateJpaSessionFactoryBean localSessionFactoryBean(){
		return new HibernateJpaSessionFactoryBean();
	}*/
	
	@Bean
	public ServletRegistrationBean<WebServlet> h2ServletRegistration(){
		ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<>(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		//registrationBean.addInitParameter("webAllowOthers", "true");
		//registrationBean.addInitParameter("webPort", "7777");
		return registrationBean;
	}
}
