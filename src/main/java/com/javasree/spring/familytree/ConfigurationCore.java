package com.javasree.spring.familytree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import com.javasree.spring.familytree.web.utils.DateFormatter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
public class ConfigurationCore extends 	WebMvcConfigurerAdapter{

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/"};

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}
	
	@Override
	public void addFormatters(org.springframework.format.FormatterRegistry registry) {
		registry.addFormatter(dateFormtter());
	};
	
	@Bean
	public DateFormatter dateFormtter(){
		return new DateFormatter();
	}
	
	@Bean
	public TemplateEngine templateEngine(){
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addTemplateResolver(new UrlTemplateResolver());
		engine.addDialect(new Java8TimeDialect());
		engine.addDialect(new LayoutDialect());
		return engine;
	}
}
