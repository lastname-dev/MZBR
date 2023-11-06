package com.mzbr.business.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzbr.business.global.exception.ErrorCode;
import com.mzbr.business.global.exception.custom.BadRequestException;
import com.mzbr.business.member.dto.MemberNicknameChangeDto;
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

	@Transactional
	public void changeNickname(MemberNicknameChangeDto memberNicknameChangeDto) {
		Member member = memberRepository.findByNickname(memberNicknameChangeDto.getNickname())
			.orElseThrow(() -> new BadRequestException(
				ErrorCode.USER_NOT_FOUND));
		if (checkNicknameIsPresent(memberNicknameChangeDto.getNickname())) {
			throw new BadRequestException(ErrorCode.NICKNAME_DUPLICATE);
		}
		member.changeNickname(memberNicknameChangeDto.getNickname());
	}
}
