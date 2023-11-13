package com.mzbr.business.global.config;

import java.util.logging.Level;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mzbr.business.oauth2.kakao.KakaoInfoFeign;
import com.mzbr.business.oauth2.kakao.KakaoTokenFeign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@Configuration
@EnableFeignClients(clients = {KakaoTokenFeign.class, KakaoInfoFeign.class})
public class FeignConfig {
	@Bean
	Level feignLoggerLevel() {
		return Level.ALL; // log레벨 설정
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			requestTemplate.header("Accept", "application/json");
		};
	}
}
