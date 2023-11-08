package com.mzbr.business.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mzbr.business.store.entity.StoreIndex;

public interface StoreRepository extends JpaRepository<StoreIndex,Long> {
}
