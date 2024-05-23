package com.ssafy.excuseMe.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//import lombok.extern.slf4j.Slf4j;


//@Slf4j
@Configuration
//@EnableAspectJAutoProxy
//@MapperScan(basePackages = { "com.ssafy.excuseMe.**.mapper" })

//servlet context에서 수행한 interceptor에 대한 설정
public class WebMvcConfiguration implements WebMvcConfigurer {
//	@Autowired
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
}
