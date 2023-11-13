package com.mzbr.business.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mzbr.business.store.entity.Store;
import com.mzbr.business.store.entity.StoreCalculation;

public interface StoreCalculationRepository extends JpaRepository<StoreCalculation, Long> {
	Optional<StoreCalculation> findByStore(Store store);
}
