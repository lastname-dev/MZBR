package com.mzbr.business.store.entity;

import javax.persistence.Embedded;

import org.springframework.data.elasticsearch.annotations.Document;

import com.mzbr.business.store.dto.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(indexName = "stores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StoreIndex {
	private Long id;

	private String name;

	private String address;

	@Embedded
	private Location location;
}
