package com.mzbr.business.store.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mzbr.business.store.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public ResponseEntity<?> searchAroundStores(@RequestParam double topLat, @RequestParam double topLong,
		@RequestParam double bottomLat, @RequestParam double bottomLong) throws
		IOException {
		storeService.searchAroundStores(topLat, topLong, bottomLat, bottomLong);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchStoresByCondition(@RequestParam double topLat, @RequestParam double topLong,
		@RequestParam double bottomLat, @RequestParam double bottomLong, @RequestParam String name,
		@RequestParam int star) throws
		IOException {
		storeService.searchByCondition(topLat, topLong, bottomLat, bottomLong, name, star);
		return ResponseEntity.ok().build();
	}
}
