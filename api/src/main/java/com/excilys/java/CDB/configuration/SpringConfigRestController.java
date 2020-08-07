package com.excilys.java.CDB.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan( {"com.excilys.java.CDB.restcontroller"})
public class SpringConfigRestController {

}
