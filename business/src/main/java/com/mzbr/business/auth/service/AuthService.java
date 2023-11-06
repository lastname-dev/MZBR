package com.mzbr.business.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzbr.business.auth.dto.SignUpDto;
import com.mzbr.business.global.exception.ErrorCode;
import com.mzbr.business.global.exception.custom.BadRequestException;
import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.Role;
import com.mzbr.business.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final MemberRepository memberRepository;

	@Transactional
	public void signup(SignUpDto signUpDto) {
		Member member = memberRepository.findById(signUpDto.getUserId())
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		if (member.getRole() == Role.MEMBER) {
			throw new BadRequestException(ErrorCode.USER_ALREADY_SIGNUP);
		}
		if (checkDuplicateNickname(signUpDto.getNickname())) {
			throw new BadRequestException(ErrorCode.NICKNAME_DUPLICATE);
		}
		member.join();
	}

	public boolean checkDuplicateNickname(String nickname) {
		return memberRepository.findByNickname(nickname).isPresent();
	}
}
