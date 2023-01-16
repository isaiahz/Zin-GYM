package com.example.zingym.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(3)
public class TrainerSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService customerTrainerDetailsService() {
		return new CustomTrainerDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder3() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider3() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customerTrainerDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder3());

		return authProvider;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider3());

		http.authorizeRequests().antMatchers("/trainer", "/trainer/login", "/trainer/logout", "/static/zingymfavicon.ico", "/trainer/class_add", "/trainer/trainer_process_register").permitAll();

		http.antMatcher("/trainer/**").authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.formLogin()
				.loginPage("/trainer/login")
				.loginProcessingUrl("/trainer/login")
				.usernameParameter("email")
				.failureHandler(new CustomAuthenticationFailureHandlerTrainer())
				.successHandler(successHandler)
//				.loginProcessingUrl("/trainee/login")
//				.defaultSuccessUrl("/trainee/trainee")
//				.failureHandler(new CustomAuthenticationFailureHandler())
//				.successHandler(successHandler)
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/trainer/logout").permitAll();
	}
	@Autowired
	private LoginSuccessHandlerTrainer successHandler;
}
