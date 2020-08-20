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
		httpSecurity.csrf().disable()
		.authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.antMatchers("/authenticate/*").permitAll()

		//roles
		.antMatchers(HttpMethod.GET, "/roles").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.POST,"/roles/page").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.GET, "/roles/{\\d+}").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.DELETE, "/roles/{\\d+}").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/roles").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.PUT, "/roles").hasAuthority("ROLE_ADMIN")
		.antMatchers("/roles/number").permitAll()
		
		//users
		.antMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.POST,"/users/page").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.GET, "/users/{\\d+}").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.DELETE, "/users/{\\d+}").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.PUT, "/users/self").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.PUT, "/users").hasAuthority("ROLE_ADMIN")
		.antMatchers("/users/number").permitAll()
		
		//companies
		.antMatchers(HttpMethod.GET, "/companies").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.POST,"/companies/page").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.GET, "/companies/{\\d+}").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.DELETE, "/companies/{\\d+}").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/companies").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.PUT, "/companies").hasAuthority("ROLE_ADMIN")
		.antMatchers("/companies/number").permitAll()
		
		//computers
		.antMatchers(HttpMethod.GET, "/computers").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.POST,"/computers/page").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.GET, "/computers/{\\d+}").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers(HttpMethod.DELETE, "/computers/{\\d+}").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/computers").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.PUT, "/computers").hasAuthority("ROLE_ADMIN")
		.antMatchers("/computers/number").permitAll()
		
		.anyRequest().authenticated().and().

		exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}