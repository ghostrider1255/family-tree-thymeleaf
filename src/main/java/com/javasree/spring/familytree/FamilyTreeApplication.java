package com.javasree.spring.familytree;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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
		try {
			Server.createTcpServer("-tcpAllowOthers","-webAllowOthers").start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new SpringApplicationBuilder(FamilyTreeApplication.class)
		//.bannerMode(Mode.CONSOLE)
		.build().run(args);
	}
}
