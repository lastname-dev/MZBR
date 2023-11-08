package com.mzbr.business.member.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.mzbr.business.member.repository.MemberRepository;
import com.mzbr.business.member.repository.SubscriptionRepository;

class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;
	@Mock
	private SubscriptionRepository subscriptionRepository;

	@Test
	void subscribe() {
		
	}

	@Test
	void getSubscribeList() {
	}
}