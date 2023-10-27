package com.mzbr.business.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.SocialType;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

	Optional<Member> findByEmail(String email);
}
