package com.mzbr.business.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
@Tag(name = "02.Restaurant")
public class RestaurantController {

	@GetMapping
	@Operation(summary = "식당 조회", description = "식당을 조회한다.")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok("zzzzzz");
	}
}
