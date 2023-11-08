package com.mzbr.business.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

	Optional<Subscription> findByFolloweeAndFollower(Member followee, Member follower);

	List<Subscription> findByFollowee(Member followee);

	List<Subscription> findByFollower(Member follower);
}
