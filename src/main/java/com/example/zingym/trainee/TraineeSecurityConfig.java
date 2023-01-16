package com.example.zingym.trainee;

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
@Order(2)
public class TraineeSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService customerTraineeDetailsService() {
		return new CustomTraineeDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder2() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider2() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customerTraineeDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder2());

		return authProvider;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider2());

		http.authorizeRequests().antMatchers("/trainee", "/trainee/login", "/trainee/logout", "/static/zingymfavicon.ico").permitAll();

		http.antMatcher("/trainee/**").authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.formLogin()
				.loginPage("/trainee/login")
				.loginProcessingUrl("/trainee/login")
				.usernameParameter("email")
				.failureHandler(new CustomAuthenticationFailureHandler())
				.successHandler(successHandler)
//				.loginProcessingUrl("/trainee/login")
//				.defaultSuccessUrl("/trainee/trainee")
//				.failureHandler(new CustomAuthenticationFailureHandler())
//				.successHandler(successHandler)
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/trainee/logout").permitAll();
	}
	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private MyLogoutSuccessHandlerTrainee myLogoutSuccessHandlerTrainee;
}
