package com.assignments.assignment7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.assignments.assignment7.filters.JwtRequestFilter;
import com.assignments.assignment7.services.MyUserDetailsService;

@EnableWebSecurity
@Configuration  
@EnableGlobalMethodSecurity(prePostEnabled = true)  

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	DataSource dataSource;
//	
	@Autowired 
	MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("luis").password("coo").roles("USER").and()
//		.withUser("lu").password("is").roles("ADMIN");
		
//		auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
//				.withUser(User.withUsername("Luis").password("Coo").roles("USER"))
//				.withUser(User.withUsername("admin").password("pass").roles("ADMIN"));
		
//		auth.jdbcAuthentication()
//			.dataSource(dataSource);
		
		auth.userDetailsService(myUserDetailsService);

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/admin").hasRole("ADMIN")
//			.antMatchers("/user").hasAnyRole("USER", "ADMIN")
//			.antMatchers("/").permitAll().and().formLogin();
		http.csrf().disable();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/authenticate", "/authenticate/createUser",
				"/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**").permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}