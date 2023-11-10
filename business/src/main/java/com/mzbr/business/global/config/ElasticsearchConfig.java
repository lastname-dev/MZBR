package com.mzbr.business.global.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.mzbr.business.store.repository.StoreElasticRepository;

@EnableElasticsearchRepositories(basePackageClasses = StoreElasticRepository.class)
@EnableFeignClients
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

	@Value(value = "${elasticsearch.host}")
	private String ELASTIC_URL;
	@Value(value = "${elasticsearch.port}")
	private String ELASTIC_PORT;
	@Value(value = "${elasticsearch.user_name}")
	private String ELASTIC_USER_NAME;
	@Value(value = "${elasticsearch.user_password}")
	private String ELASTIC_USER_PASSWORD;

	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
			.connectedTo(ELASTIC_URL + ":" + ELASTIC_PORT)
			.withBasicAuth(ELASTIC_USER_NAME, ELASTIC_USER_PASSWORD)
			.build();
		return RestClients.create(clientConfiguration).rest();

	}

	@Bean
	public ElasticsearchOperations elasticsearchOperations() {
		return new ElasticsearchRestTemplate(elasticsearchClient());
	}

}
