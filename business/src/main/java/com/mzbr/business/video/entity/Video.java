package com.mzbr.business.video.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mzbr.business.member.entity.Member;
import com.mzbr.business.store.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "video")
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String videoUuid;
	String thumbnailUrl;

	@OneToOne(mappedBy = "videoEntity")
	VideoData videoData;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	Store store;
}
