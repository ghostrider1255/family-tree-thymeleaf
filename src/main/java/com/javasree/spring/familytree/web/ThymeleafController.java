package com.javasree.spring.familytree.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {
	
	private static final Logger log = LoggerFactory.getLogger(ThymeleafController.class);

	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		model.put("message", "test message from controller");
		log.info("in ThymeleafController class");
		return "welcome";
	}
	
}
