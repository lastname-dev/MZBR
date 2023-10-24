package com.mzbr.business.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {

		Info info = new Info()
			.title("MZBR Business Server API Document")
			.version("v0.0.1")
			.description("MZBR 프로젝트 Business Server의 API 명세서입니다.")
			.contact(new Contact().name("MZBR")
				.url("https://github.com/MZBR-2023/business-service")
				.email("lanto12@naver.com"))
			.license(
				new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

		return new OpenAPI()
			.components(new Components())
			.info(info);
	}
}
