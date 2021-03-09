package com.javabrains.springsecrityjdbc.config;

import javax.annotation.security.PermitAll;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource datasource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.dataSource(datasource);

		// Default Query we can change schema and here change table name		
//		.usersByUsernameQuery("select username,password,enable from users where username = ?")
//		.authoritiesByUsernameQuery("select username,authority from authorities where username =?");
		
		
		
//		.withDefaultSchema()
//		.withUser(User.withUsername("user") 
//				.password("pass")
//				.roles("USER") )
//		.withUser(User.withUsername("admin") 
//				.password("pass1")
//				.roles("ADMIN") );
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/user").hasAnyRole("USER","ADMIN")
			.antMatchers("/").permitAll()
			.and().formLogin();
		
			
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder () {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
