package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.CustomUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	private final CustomUserService customUserService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
				.authorizeRequests()
				.antMatchers("/", "/login", "/register", "/expenses")
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.formLogin().loginPage("/login")
				.defaultSuccessUrl("/expenses")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				.logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll();
				
				
				
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/resources/**", "/js/**", "/css/**", "/css/images/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(customUserService);
	}
	
	

}
