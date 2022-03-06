package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DemoRestApiCallCheckService {

	/** ユーザーデータを追加するURL */
	private static final String POST_USER = "http://localhost:8085/users";

	/** id=3のユーザーデータを更新するURL */
	private static final String PUT_USER_3 = "http://localhost:8085/users/3";

	/** RestTemplateオブジェクト */
	@Autowired
	private RestTemplate restTemplate;

	/** HttpHeadersオブジェクト */
	@Autowired
	private HttpHeaders httpHeaders;
	
	/** ObjectMapperオブジェクト */
	@Autowired
	private ObjectMapper objectMapper;

	/** ログ出力のためのクラス */
	private static Log log = LogFactory.getLog(DemoRestApiCallCheckService.class);

	/**
	 * Rest APIでデータ追加・更新時にチェックエラーになる処理の呼び出しを実行する.
	 */
	public void execRestApiCheck() {

		log.debug("*** USER_DATAテーブルに(名前の)チェックエラーとなるデータを追加しようとした結果 ***");	
		try {
			UserData newUserData = new UserData(4, "", 2012, 2, 25, "1", "テスト４");
			restTemplate.exchange(POST_USER, HttpMethod.POST, new HttpEntity<>(newUserData, httpHeaders),
					UserData.class);
			log.debug("(名前の)チェックエラーとなるため、この部分は通過しない");
		} catch (HttpClientErrorException ex) {
			try {
				DemoExceptionResponse demoExceptionResponse = objectMapper.readValue(
						ex.getResponseBodyAsString(), DemoExceptionResponse.class);
				log.error("発生したエラーメッセージ : " + demoExceptionResponse.getMessage());
				log.debug("エラー時のレスポンス内容 : " + demoExceptionResponse);
				log.debug("");
			} catch (Exception ex2) {
				log.error(ex2);
				return;
			}
		}
		
		log.debug("*** USER_DATAテーブルに(生年月日の)チェックエラーとなるデータを追加しようとした結果 ***");	
		try {
			UserData newUserData = new UserData(4, "テスト　プリン４", 2012, 2, 30, "1", "テスト４");
			restTemplate.exchange(POST_USER, HttpMethod.POST, new HttpEntity<>(newUserData, httpHeaders),
					UserData.class);
			log.debug("(生年月日の)チェックエラーとなるため、この部分は通過しない");
		} catch (HttpClientErrorException ex) {
			try {
				DemoExceptionResponse demoExceptionResponse = objectMapper.readValue(
						ex.getResponseBodyAsString(), DemoExceptionResponse.class);
				log.error("発生したエラーメッセージ : " + demoExceptionResponse.getMessage());
				log.debug("エラー時のレスポンス内容 : " + demoExceptionResponse);
				log.debug("");
			} catch (Exception ex2) {
				log.error(ex2);
				return;
			}
		}
		
		log.debug("*** USER_DATAテーブルに(性別の)チェックエラーとなるデータを追加しようとした結果 ***");	
		try {
			UserData newUserData = new UserData(4, "テスト　プリン４", 2012, 2, 25, "3", "テスト４");
			restTemplate.exchange(POST_USER, HttpMethod.POST, new HttpEntity<>(newUserData, httpHeaders),
					UserData.class);
			log.debug("(性別の)チェックエラーとなるため、この部分は通過しない");
		} catch (HttpClientErrorException ex) {
			try {
				DemoExceptionResponse demoExceptionResponse = objectMapper.readValue(
						ex.getResponseBodyAsString(), DemoExceptionResponse.class);
				log.error("発生したエラーメッセージ : " + demoExceptionResponse.getMessage());
				log.debug("エラー時のレスポンス内容 : " + demoExceptionResponse);
				log.debug("");
			} catch (Exception ex2) {
				log.error(ex2);
				return;
			}
		}
		
		log.debug("*** USER_DATAテーブルに(名前・生年月日・性別の)チェックエラーとなるデータを追加しようとした結果 ***");	
		try {
			UserData newUserData = new UserData(4, "", 2012, 2, 30, "3", "テスト４");
			restTemplate.exchange(POST_USER, HttpMethod.POST, new HttpEntity<>(newUserData, httpHeaders),
					UserData.class);
			log.debug("(名前・生年月日・性別の)チェックエラーとなるため、この部分は通過しない");
		} catch (HttpClientErrorException ex) {
			try {
				DemoExceptionResponse demoExceptionResponse = objectMapper.readValue(
						ex.getResponseBodyAsString(), DemoExceptionResponse.class);
				log.error("発生したエラーメッセージ : " + demoExceptionResponse.getMessage());
				log.debug("エラー時のレスポンス内容 : " + demoExceptionResponse);
				log.debug("");
			} catch (Exception ex2) {
				log.error(ex2);
				return;
			}
		}
		
		log.debug("*** USER_DATAテーブルに(名前・生年月日・性別の)チェックエラーとなるデータを更新しようとした結果 ***");	
		try {
			UserData updUserData = new UserData(0, "", 2012, 2, 30, "3", "テスト４");
			restTemplate.exchange(PUT_USER_3, HttpMethod.PUT, new HttpEntity<>(updUserData, httpHeaders),
					UserData.class);
			log.debug("(名前・生年月日・性別の)チェックエラーとなるため、この部分は通過しない");
		} catch (HttpClientErrorException ex) {
			try {
				DemoExceptionResponse demoExceptionResponse = objectMapper.readValue(
						ex.getResponseBodyAsString(), DemoExceptionResponse.class);
				log.error("発生したエラーメッセージ : " + demoExceptionResponse.getMessage());
				log.debug("エラー時のレスポンス内容 : " + demoExceptionResponse);
				log.debug("");
			} catch (Exception ex2) {
				log.error(ex2);
				return;
			}
		}
	}
}
