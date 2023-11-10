package com.mzbr.business.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SquareLocation {
	private double topLat;
	private double topLng;
	private double bottomLat;
	private double bottomLng;

	public static SquareLocation of(double topLat, double topLng, double bottomLat, double bottomLng) {
		return SquareLocation.builder()
			.topLat(topLat)
			.topLng(topLng)
			.bottomLat(bottomLat)
			.bottomLng(bottomLng)
			.build();
	}

}
