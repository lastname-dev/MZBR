package com.mzbr.business.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.mzbr.business.store.dto.StoreDto;
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
	public List<StoreDto> searchAroundStores(StoreSearchDto storeSearchDto) throws IOException {
		SquareLocation location = storeSearchDto.getSquareLocation();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
			.query(new GeoBoundingBoxQueryBuilder(LOCATION_FIELD).setCorners(new GeoPoint(location.getTopLat(), location.getBottomLng()),
				new GeoPoint(location.getBottomLat(), location.getTopLng())));

		StoreResultDto storeResultDto = executeSearchRequest(searchSourceBuilder);

		return mapToStoreDtoList(storeResultDto) ;
	}

	@Transactional
	public List<StoreDto> searchByCondition(StoreSearchDto storeSearchDto) throws IOException {
		SearchSourceBuilder searchSourceBuilder = buildSearchSourceBuilder(storeSearchDto);

		StoreResultDto storeResultDto = executeSearchRequest(searchSourceBuilder);

		return mapToStoreDtoList(storeResultDto);
	}

	private SearchSourceBuilder buildSearchSourceBuilder(StoreSearchDto storeSearchDto) {
		SquareLocation squareLocation = storeSearchDto.getSquareLocation();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchQuery(NAME_FIELD, storeSearchDto.getName()));
		boolQueryBuilder.must(new GeoBoundingBoxQueryBuilder(LOCATION_FIELD)
			.setCorners(new GeoPoint(squareLocation.getTopLat(), squareLocation.getBottomLng()),
				new GeoPoint(squareLocation.getBottomLat(), squareLocation.getTopLng())));
		searchSourceBuilder.query(boolQueryBuilder);
		return searchSourceBuilder;
	}

	private StoreResultDto executeSearchRequest(SearchSourceBuilder searchSourceBuilder) throws IOException {
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME).source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String resultJson = searchResponse.toString();
		return objectMapper.readValue(resultJson, StoreResultDto.class);
	}

	private List<StoreDto> mapToStoreDtoList(StoreResultDto storeResultDto) {
		return Arrays.stream(storeResultDto.getHits().getHits())
			.map(hit -> StoreDto.from(hit.get_source()))
			.collect(Collectors.toList());
	}

}
