package com.example.WeCanScapeApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*");
			}
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeRequests()
				.requestMatchers("/**").permitAll();
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean
	 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception {
	 * http
	 * .cors(withDefaults()) // Enable CORS with default settings
	 * .authorizeHttpRequests(authorize -> authorize
	 * .requestMatchers("/**").permitAll() // Allow preflight requests
	 * .anyRequest().permitAll() // Allow all other requests without authentication
	 * );
	 * return http.build();
	 * }
	 * 
	 * @Bean
	 * public WebMvcConfigurer corsConfig() {
	 * return new WebMvcConfigurer() {
	 * 
	 * @Override
	 * public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**")
	 * .allowedOrigins("*")
	 * .allowedMethods("*")
	 * .allowedHeaders("*");
	 * System.out.println("CORS configuration applied");
	 * }
	 * };
	 * }
	 * 
	 * @Bean
	 * public UserDetailsService userDetailsService() {
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(
	 * User.withUsername("admin").password(passwordEncoder().encode("admin")).roles(
	 * "USER").build());
	 * return manager;
	 * }
	 * 
	 * @Bean
	 * public PasswordEncoder passwordEncoder() {
	 * return new BCryptPasswordEncoder();
	 * }
	 */
}