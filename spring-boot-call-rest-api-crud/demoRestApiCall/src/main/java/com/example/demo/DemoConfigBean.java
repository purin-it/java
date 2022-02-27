package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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
}
