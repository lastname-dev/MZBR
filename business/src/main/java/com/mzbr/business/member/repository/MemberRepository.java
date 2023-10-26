package com.mzbr.business.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mzbr.business.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
