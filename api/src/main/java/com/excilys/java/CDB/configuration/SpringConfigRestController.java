package com.excilys.java.CDB.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.excilys.java.CDB.jwt.JwtAuthenticationEntryPoint;
import com.excilys.java.CDB.jwt.JwtRequestFilter;


@EnableWebMvc
@Configuration
@ComponentScan( {"com.excilys.java.CDB.restcontroller", "com.excilys.java.CDB.jwt"})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringConfigRestController extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
		.antMatchers("/authenticate/**").permitAll()

		//roles
		.antMatchers(HttpMethod.DELETE, "/roles/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.POST,"/roles/page/**").hasAnyAuthority("ROLE_user","ROLE_admin")
		.antMatchers(HttpMethod.POST, "/roles/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.PUT, "/roles/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.GET, "/roles/**").hasAnyAuthority("ROLE_user","ROLE_admin")

		//users
		.antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.POST,"/users/page/**").hasAnyAuthority("ROLE_user","ROLE_admin")
		.antMatchers(HttpMethod.POST, "/users/**").hasAuthority("ROLE_admin")
//		.antMatchers(HttpMethod.PUT, "/users/self/**").hasAnyAuthority("ROLE_user","ROLE_admin")
		.antMatchers(HttpMethod.PUT, "/users/").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ROLE_user","ROLE_admin")

		//companies
		.antMatchers(HttpMethod.DELETE, "/companies/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.POST,"/companies/page/**").hasAnyAuthority("ROLE_user","ROLE_admin")
		.antMatchers(HttpMethod.POST, "/companies/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.PUT, "/companies/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.GET, "/companies/**").hasAnyAuthority("ROLE_user","ROLE_admin")

		//computers
		.antMatchers(HttpMethod.DELETE, "/computers/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.POST,"/computers/page/**").hasAnyAuthority("ROLE_user","ROLE_admin")
		.antMatchers(HttpMethod.POST, "/computers/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.PUT, "/computers/**").hasAuthority("ROLE_admin")
		.antMatchers(HttpMethod.GET, "/computers/**").hasAnyAuthority("ROLE_user","ROLE_admin")

		.and().csrf().disable().
		exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}