package com.cinehub.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;




@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cinehub")
public class WebMvcConfig {
    // message converters, view resolvers, CORS etc. can be added later
}


//WebMvcConfig + WebAppInitializer configure the Spring MVC web layer (controllers, DispatcherServlet) in Java (no web.xml required).
