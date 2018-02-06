package com.javasree.spring.familytree;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages={"com.javasree.spring.familytree.model"})
@EnableJpaRepositories(basePackages = {"com.javasree.spring.familytree.web.jpa"})
public class FamilyTreeApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(FamilyTreeApplication.class);
	}
	public static void main(String[] args) {
		new SpringApplicationBuilder(FamilyTreeApplication.class)
		//.bannerMode(Mode.CONSOLE)
		.build().run(args);
	}
}
