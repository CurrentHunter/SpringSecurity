package com.example.security.secconfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
//				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(Roles.STUDENT.name())
//				.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(UserPermissions.COURSE_WRITE.getPermission())			
//				.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(UserPermissions.COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(UserPermissions.COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(Roles.ADMIN.name(), Roles.ADMIN_TRAINEE.name())			
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/courses", true);
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails mariaUser = User.builder()
				.username("maria")
				.password(passwordEncoder.encode("password"))
//				.roles(Roles.STUDENT.name())
				.authorities(Roles.STUDENT.getGrantedAuthority())
				.build();
		
		UserDetails bobUser = User.builder()
			.username("bob")
			.password(passwordEncoder.encode("password123"))
//			.roles(Roles.ADMIN.name())
			.authorities(Roles.ADMIN.getGrantedAuthority())
			.build();
			
		UserDetails tomUser = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("password123"))
//				.roles(Roles.ADMIN_TRAINEE.name())
				.authorities(Roles.ADMIN_TRAINEE.getGrantedAuthority())
				.build();
		
		return new InMemoryUserDetailsManager (
				mariaUser, bobUser, tomUser
				);
	}
}
