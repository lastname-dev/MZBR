package com.mzbr.business.member.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mzbr.business.global.exception.ErrorCode;
import com.mzbr.business.global.exception.custom.AWSException;
import com.mzbr.business.global.exception.custom.BadRequestException;
import com.mzbr.business.global.s3.S3UploadService;
import com.mzbr.business.member.dto.MemberDto;
import com.mzbr.business.member.dto.MemberNicknameChangeDto;
import com.mzbr.business.member.dto.MemberSubscribeDto;
import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.Subscription;
import com.mzbr.business.member.repository.MemberRepository;
import com.mzbr.business.member.repository.SubscriptionRepository;
import com.mzbr.business.video.entity.Video;
import com.mzbr.business.video.repository.VideoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final VideoRepository videoRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final S3UploadService s3UploadService;

	public boolean checkNicknameIsPresent(String nickname) {
		Optional<Member> member = memberRepository.findByNickname(nickname);
		return member.isPresent();
	}

	public MemberDto getMyInfo(long userId) {
		Member member = memberRepository.findById(userId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		List<Video> videos = videoRepository.findByMember(member);
		Long videoCount = (long)videos.size();
		List<Subscription> following = subscriptionRepository.findByFollower(member);
		Long subscriptionCount = (long)following.size();
		MemberDto memberDto = MemberDto.of(member, videoCount, subscriptionCount);
		return memberDto;
	}

	public MemberDto getOtherInfo(long myId, long userId) {
		Member me = memberRepository.findById(myId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		Member member = memberRepository.findById(userId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		List<Video> videos = videoRepository.findByMember(member);
		Long videoCount = (long)videos.size();
		List<Subscription> following = subscriptionRepository.findByFollower(member);
		Long subscriptionCount = (long)following.size();
		Optional<Subscription> byFolloweeAndFollower = subscriptionRepository.findByFolloweeAndFollower(member, me);
		MemberDto memberDto = MemberDto.of(member, videoCount, subscriptionCount, byFolloweeAndFollower.isPresent());
		return memberDto;
	}

	@Transactional
	public void changeNickname(MemberNicknameChangeDto memberNicknameChangeDto) {
		Member member = memberRepository.findById(memberNicknameChangeDto.getUserId())
			.orElseThrow(() -> new BadRequestException(
				ErrorCode.USER_NOT_FOUND));
		if (checkNicknameIsPresent(memberNicknameChangeDto.getNickname())) {
			throw new BadRequestException(ErrorCode.NICKNAME_DUPLICATE);
		}
		member.changeNickname(memberNicknameChangeDto.getNickname());
	}

	@Transactional
	public void changeProfileImage(MultipartFile image, long memberId) {
		try {
			String url = s3UploadService.upload(image);
			Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
			member.changeProfileImage(url);
		} catch (IOException e) {
			log.error("error : {}", e.getMessage());
			throw new AWSException(ErrorCode.AWS_S3_UPLOAD_EXCEPTION);
		}
	}

	@Transactional
	public void subscribe(MemberSubscribeDto memberSubscribeDto) {
		Member follower = memberRepository.findById(memberSubscribeDto.getFollowerId())
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		Member followee = memberRepository.findById(memberSubscribeDto.getFolloweeId())
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

		if (follower == followee)
			throw new BadRequestException(ErrorCode.FOLLOW_BAD_REQUEST);

		subscriptionRepository.findByFolloweeAndFollower(followee, follower)
			.ifPresentOrElse(
				Subscription::subscribe,
				() -> subscriptionRepository.save(Subscription.of(follower, followee))
			);
	}

	public List<MemberDto> getSubscribeList(long userId) {
		Member member = memberRepository.findById(userId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
		List<Subscription> subscriptions = subscriptionRepository.findByFollowerAndIsExistedIsTrue(member);
		List<Member> follows = subscriptions.stream().map(Subscription::getFollowee).toList();
		List<MemberDto> list = follows.stream().map(MemberDto::from).toList();
		return list;
	}
}
