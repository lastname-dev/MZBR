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
public class StoreDto {
	private long storeId;
	private String storeName;
	private double latitude;
	private double longitude;
	private String address;
	private float starCount;
	
	public static StoreDto from(StoreResultDto.Source source){
		return StoreDto.builder()
			.storeId(source.getId())
			.storeName(source.getName())
			.latitude(source.getLocation().getLat())
			.longitude(source.getLocation().getLon())
			.address(source.getAddress())
			.build();
	}
	

}
