package com.example.zingym.admin;


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
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomAdminDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider1());

        http.authorizeRequests().antMatchers("/admin", "/admin/login", "/admin/logout", "/static/zingymfavicon.ico", "/admin/add_activity").permitAll();

        http.antMatcher("/admin/**")
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("email")
//                .loginProcessingUrl("/admin/login")
//                .defaultSuccessUrl("/admin/home")
                .failureHandler(new CustomAuthenticationFailureHandlerAdmin())
                .successHandler(successHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout").permitAll();

    }

    @Autowired
    private AdminLoginSuccessHandler successHandler;

}