package com.mzbr.business.member.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mzbr.business.member.dto.SubScriptionId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
@IdClass(SubScriptionId.class)
public class Subscription implements Serializable {
	@Id
	@ManyToOne
	@JoinColumn(name = "follower")
	private Member follower;

	@Id
	@ManyToOne
	@JoinColumn(name = "followee")
	private Member followee;

	private boolean isExisted;

	public static Subscription of(Member follower, Member followee) {
		return Subscription.builder()
			.follower(follower)
			.followee(followee)
			.build();
	}

	public void subscribe() {
		this.isExisted = !this.isExisted;
	}
}
