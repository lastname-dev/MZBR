package com.mzbr.business.store.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Location {
	@Column(nullable = false)
	double lat;
	@Column(nullable = false)
	double lon;
}
