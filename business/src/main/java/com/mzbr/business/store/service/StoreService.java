package com.mzbr.business.store.service;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzbr.business.store.dto.SquareLocation;
import com.mzbr.business.store.dto.StoreResultDto;
import com.mzbr.business.store.dto.StoreSearchDto;
import com.mzbr.business.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoreService {

	private final ElasticsearchOperations elasticsearchOperations;
	private final StoreRepository storeRepository;
	private final RestHighLevelClient client;
	private final ObjectMapper objectMapper;

	@Value("${elasticsearch.indecies.storesIndexName}")
	private String INDEX_NAME;
	private static final String NAME_FIELD = "name";
	private static final String LOCATION_FIELD = "location";

	@Transactional
	public StoreResultDto searchAroundStores(double topLat, double topLong, double bottomLat, double bottomLong) throws
		IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
			.query(new GeoBoundingBoxQueryBuilder(LOCATION_FIELD).setCorners(new GeoPoint(topLat, bottomLong),
				new GeoPoint(bottomLat, topLong)));

		SearchRequest searchRequest = new SearchRequest(INDEX_NAME).source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String resultJson = searchResponse.toString();
		StoreResultDto storeResultDto = objectMapper.readValue(resultJson, StoreResultDto.class);
		return storeResultDto;
	}

	@Transactional
	public StoreResultDto searchByCondition(StoreSearchDto storeSearchDto) throws
		IOException {
		SquareLocation squareLocation = storeSearchDto.getSquareLocation();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must(QueryBuilders.matchQuery(NAME_FIELD, storeSearchDto.getName()));

		boolQueryBuilder.must(new GeoBoundingBoxQueryBuilder(LOCATION_FIELD)
			.setCorners(new GeoPoint(squareLocation.getTopLat(), squareLocation.getBottomLng()),
				new GeoPoint(squareLocation.getBottomLat(), squareLocation.getTopLng())));

		searchSourceBuilder.query(boolQueryBuilder);

		SearchRequest searchRequest = new SearchRequest(INDEX_NAME).source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		String resultJson = searchResponse.toString();
		StoreResultDto storeResultDto = objectMapper.readValue(resultJson, StoreResultDto.class);
		return storeResultDto;
		// log.info("result : {}", storeResultDto.getHits().getHits()[0].get_source().getName());
	}

	public void changeStar() {
		
	}
}
