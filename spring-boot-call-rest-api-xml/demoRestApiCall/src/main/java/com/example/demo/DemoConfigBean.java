package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class DemoConfigBean {

	/**
	 * RestTemplateオブジェクトを作成する
	 * @return RestTemplateオブジェクト
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * ContentType：JSONであるHttpHeadersオブジェクトを作成する
	 * @return HttpHeadersオブジェクト
	 */
	@Bean
	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	/**
	 * ObjectMapperオブジェクトを作成する
	 * @return ObjectMapperオブジェクト
	 */
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
}
