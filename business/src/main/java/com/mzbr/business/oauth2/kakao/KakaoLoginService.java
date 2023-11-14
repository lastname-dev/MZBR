package com.mzbr.business.oauth2.kakao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mzbr.business.global.jwt.JwtService;
import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.Role;
import com.mzbr.business.member.entity.SocialType;
import com.mzbr.business.member.repository.MemberRepository;
import com.mzbr.business.oauth2.kakao.dto.KakaoInfoResponseDto;
import com.mzbr.business.oauth2.kakao.dto.KakaoTokenResponseDto;
import com.mzbr.business.oauth2.kakao.dto.UserOauthInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoClientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;

	private final String contextType = "application/x-www-form-urlencoded;charset=utf-8";

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirectUri;

	private final KakaoTokenFeign kakaoTokenFeign;
	private final KakaoInfoFeign kakaoInfoFeign;
	private final MemberRepository memberRepository;
	private final JwtService jwtService;

	@Transactional
	public String getKakaoAccessToken(String code) {
		//accessToken 가지고 오기
		KakaoTokenResponseDto.Response token = kakaoTokenFeign.requestKakaoToken("authorization_code", kakaoClientId,
			kakaoClientSecret, redirectUri, code);

		return token.getAccessToken();
	}

	public UserOauthInfo getUserInfo(String token) {
		KakaoInfoResponseDto kakaoMemberInfo = kakaoInfoFeign.getKakaoMemberInfo("Bearer " + token);
		UserOauthInfo userOauthInfo = UserOauthInfo.of(kakaoMemberInfo.getKakaoId(),
			kakaoMemberInfo.getKakaoAccount().getProfile().getNickname());

		Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO,
			userOauthInfo.getSocial_id());
		if (member.isPresent()) {
			String accessToken = jwtService.createAccessToken(member.get().getId());
			userOauthInfo.setOwnJwtAccessToken(accessToken);
			if (member.get().getRole().equals(Role.MEMBER)) {
				String refreshToken = jwtService.createRefreshToken();
				jwtService.saveRefreshToken(refreshToken, member.get().getId());
				userOauthInfo.setSocial_id(member.get().getId() + "");
				userOauthInfo.setNickName(member.get().getNickname());
				userOauthInfo.setOwnJwtRefreshToken(refreshToken);
				userOauthInfo.setUser(true);
			}
			return userOauthInfo;
		}
		Member newMember = memberRepository.save(Member.of(SocialType.KAKAO, userOauthInfo));
		userOauthInfo.setOwnJwtAccessToken(jwtService.createAccessToken(newMember.getId()));
		return userOauthInfo;

	}

}
