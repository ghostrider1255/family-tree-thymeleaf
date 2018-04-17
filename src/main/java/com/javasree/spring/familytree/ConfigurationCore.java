package com.javasree.spring.familytree;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import com.javasree.spring.familytree.web.utils.DateFormatter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
public class ConfigurationCore implements WebMvcConfigurer{

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
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory multipartFactory = new MultipartConfigFactory();
		multipartFactory.setMaxFileSize("50MB");
		multipartFactory.setMaxRequestSize("50MB");
		return multipartFactory.createMultipartConfig();
	}
}
