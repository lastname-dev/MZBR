package com.mzbr.business.global.jwt;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.mzbr.business.global.exception.ErrorCode;
import com.mzbr.business.global.exception.custom.AuthException;
import com.mzbr.business.user.entity.User;
import com.mzbr.business.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAutenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	@Value("${uri.permits}")
	private final List<String> permitUrl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {

		if (isPermitURI(request.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		String refreshToken = jwtService.extractRefreshToken(request)
			.filter(jwtService::isTokenValid)
			.orElse(null);

		if (refreshToken != null)
			jwtService.checkRefreshToken(response, refreshToken);

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
