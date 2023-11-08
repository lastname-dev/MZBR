package com.mzbr.business.store.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.mzbr.business.store.entity.StoreIndex;

public interface StoreElasticRepository extends ElasticsearchRepository<StoreIndex,Long> {
}
