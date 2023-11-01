package com.mzbr.business.oauth2.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mzbr.business.global.exception.ErrorCode;
import com.mzbr.business.global.exception.custom.BadRequestException;
import com.mzbr.business.global.jwt.JwtService;
import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.Role;
import com.mzbr.business.member.repository.MemberRepository;
import com.mzbr.business.oauth2.CustomOAuth2User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();

		Member member = memberRepository.findByEmail(oAuth2User.getEmail())
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		String accessToken = jwtService.createAccessToken(member.getId());
		String refreshToken = jwtService.createRefreshToken();
		jwtService.saveRefreshToken(refreshToken, member.getId());
		if (member.getRole().equals(Role.GUEST)) {
			jwtService.sendRedirect(response, accessToken, refreshToken, false);
		}
		jwtService.sendRedirect(response, accessToken, refreshToken, true);

	}
}
