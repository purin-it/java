package com.example.demo;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class DemoSwaggerConfig {

	private HashSet<String> consumesAndProduces = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));
	
	@Bean
	public Docket api() {
		// Swaggerでドキュメント生成に必要な設定を生成し返却
		return new Docket(DocumentationType.SWAGGER_2)
				   // API情報を設定
				.apiInfo(apiInfo())
				   // 送信するAPIのデータタイプを設定
				.consumes(consumesAndProduces)
				   // 受信するAPIのデータタイプを設定
				.produces(consumesAndProduces);
	}
	
	private ApiInfo apiInfo() {
		// API情報を生成し返却
	    return new ApiInfoBuilder()
	    	    // タイトル・詳細説明・APIバージョン
	        .title("ユーザー情報登録・更新API")
	        .description("ユーザー情報の登録・更新・削除ができるAPIです")
	        .version("1.0.0")
	            // 連絡先(連絡先名・サイトURL・メールアドレス)
	        .contact(new Contact("purin-it"
	        		, "https://www.purin-it.com", "tagyo483@f5.si"))
	            // ライセンス名・ライセンスURL
	        .license("Apache 2.0")  
	        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	        .build();
	  }
}
