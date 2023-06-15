package com.sporty.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sporty.service.CustomUserDetailService;

// Authentication
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .authorizeRequests()
		    .antMatchers("/","/shop/**","/forgotpassword","/register","/h2-console/**").permitAll()
		    .antMatchers("/admin/**").hasRole("ADMIN")
		    .anyRequest()
		    .authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/login")
		    .permitAll() 
		    .failureUrl("/login?error=true")
		    .defaultSuccessUrl("/")
		    .usernameParameter("email")
		    .passwordParameter("password")
		    .and()
		    .oauth2Login()
		    .loginPage("/login") 
		    .successHandler(googleOAuth2SuccessHandler)
		    .and()
		    .logout()
		    .logoutSuccessUrl("/login")
		    .invalidateHttpSession(true)
		    .deleteCookies("JESSIONID")
		    .and()
		    .exceptionHandling()
		    .and()
		    .csrf()
		    .disable();
		
		http.headers().frameOptions().disable(); // for h2 console
	}
   
	//password encoder
	
	@Bean
	public BCryptPasswordEncoder bCryPasswordEncoder() {
  return new BCryptPasswordEncoder();		
	}

	// configure authentication manager builder
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**","/images/**","/productImages/**","/css/**","/js/**");
	}
	
}
