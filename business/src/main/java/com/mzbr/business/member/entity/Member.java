package com.mzbr.business.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mzbr.business.oauth2.userinfo.OAuth2UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberId")
	private int id;

	private String email; // 이메일

	private String nickname; // 닉네임

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	private String socialId;

	public void authorizeUser() {
		this.role = Role.MEMBER;
	}

	public static Member of(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
		return Member.builder()
			.socialType(socialType)
			.socialId(oAuth2UserInfo.getId())
			.nickname(oAuth2UserInfo.getNickname())
			.role(Role.GUEST)
			.build();
	}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}

	public void join(String nickname) {
		this.nickname = nickname;
		this.role = Role.MEMBER;
	}

}
