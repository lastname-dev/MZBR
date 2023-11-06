package com.mzbr.business.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public boolean checkNicknameIsPresent(String nickname) {
		Optional<Member> member = memberRepository.findByNickname(nickname);
		return member.isPresent();
	}
}
