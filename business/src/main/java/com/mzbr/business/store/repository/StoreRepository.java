package com.mzbr.business.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mzbr.business.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
