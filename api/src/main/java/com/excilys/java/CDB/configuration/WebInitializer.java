package com.excilys.java.CDB.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer  implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		 AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
	     webApplicationContext.register(SpringConfigRestController.class, SpringConfigService.class, SpringConfigPersistence.class);
		 webApplicationContext.setServletContext(servletContext);
	     ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
	     servlet.setLoadOnStartup(1);
	     servlet.addMapping("/");
	}

}
