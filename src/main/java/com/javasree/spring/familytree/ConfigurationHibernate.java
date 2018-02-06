package com.javasree.spring.familytree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationHibernate {
	
	@Bean(name="sessionFactory")
	public HibernateJpaSessionFactoryBean localSessionFactoryBean(){
		return new HibernateJpaSessionFactoryBean();
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
