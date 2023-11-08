package com.mzbr.business.global.jwt;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAutenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	@Value("${uri.permits}")
	private final List<String> permitUrl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException,
		IOException {
		if (isPermitURI(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String refreshToken = jwtService.extractRefreshToken(request)
			.filter(jwtService::isTokenValid)
			.orElse(null);

		if (refreshToken != null) {
			jwtService.checkRefreshToken(request, response, refreshToken);
		} else {
			jwtService.checkAccessToken(request, response, filterChain);
		}
	}

	public boolean isPermitURI(String uri) {
		for (String permitUrl : permitUrl) {
			if (pathMatcher.match(permitUrl, uri)) {
				return true;
			}
		}
		return false;
	}
}
