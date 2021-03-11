package com.wonju.work.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebserviceConfig implements WebMvcConfigurer{
	
	@Autowired
	AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor).addPathPatterns("/select_data").addPathPatterns("/search_data")
			.excludePathPatterns("/css/**").excludePathPatterns("/img/**").excludePathPatterns("/home");
		
		
	}

}
