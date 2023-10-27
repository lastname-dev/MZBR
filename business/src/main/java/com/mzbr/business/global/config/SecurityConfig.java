package com.mzbr.business.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mzbr.business.global.exception.ExceptionHandlerFilter;
import com.mzbr.business.global.jwt.JwtAutenticationFilter;
import com.mzbr.business.global.jwt.JwtService;
import com.mzbr.business.oauth2.handler.OAuth2LoginFailureHandler;
import com.mzbr.business.oauth2.handler.OAuth2LoginSuccessHandler;
import com.mzbr.business.oauth2.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Value(value = "${uri.permits}")
	private final List<String> permitUrl;
	private final JwtService jwtService;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.headers().frameOptions().disable()
			.xssProtection().and().contentSecurityPolicy("script-src 'self'").and().and()
			.cors().and()

			.authorizeRequests()
			.antMatchers(permitUrl.toArray(String[]::new)).permitAll()
			.anyRequest().authenticated().and()

			.oauth2Login()
			.successHandler(oAuth2LoginSuccessHandler)
			.failureHandler(oAuth2LoginFailureHandler)
			.userInfoEndpoint().userService(customOAuth2UserService);
		
		http.addFilterAfter(jwtAuthenticationFilter(), LogoutFilter.class);
		http.addFilterBefore(new ExceptionHandlerFilter(), JwtAutenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// configuration.addAllowedOrigin("http://localhost:3000/");
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		// provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}

	@Bean
	public JwtAutenticationFilter jwtAuthenticationFilter() {
		return new JwtAutenticationFilter(jwtService, permitUrl);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
