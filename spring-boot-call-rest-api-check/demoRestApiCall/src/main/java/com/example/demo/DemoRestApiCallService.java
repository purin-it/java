package com.example.demo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoRestApiCallService {

	/** 全てのユーザーデータを取得するURL */
	private static final String GET_ALL_USERS = "http://localhost:8085/users";
	
	/** id=1のユーザーデータを取得するURL */
	private static final String GET_USER_1 = "http://localhost:8085/users/1";
	
	/** id=4のユーザーデータを取得するURL */
	private static final String GET_USER_4 = "http://localhost:8085/users/4";
	
	/** id=10のユーザーデータを取得するURL */
	private static final String GET_USER_10 = "http://localhost:8085/users/10";
	
	/** id=aのユーザーデータを取得するURL */
	private static final String GET_USER_a = "http://localhost:8085/users/a";
	
	/** ユーザーデータを追加するURL */
	private static final String POST_USER = "http://localhost:8085/users";
	
	/** id=4のユーザーデータを更新するURL */
	private static final String PUT_USER_4 = "http://localhost:8085/users/4";
	
	/** id=4のユーザーデータを削除するURL */
	private static final String DELETE_USER_4 = "http://localhost:8085/users/4";
	
	/** RestTemplateオブジェクト */
	@Autowired
	private RestTemplate restTemplate;
	
	/** HttpHeadersオブジェクト */
	@Autowired
	private HttpHeaders httpHeaders;
	
	/** ログ出力のためのクラス */
	private static Log log = LogFactory.getLog(DemoRestApiCallService.class);
	
	/**
	 * Rest APIの各種呼び出しを実行する.
	 */
	public void execRestApi() {
		log.debug("*** USER_DATAテーブルから全データを取得した結果 ***");
		
		// user_dataテーブルのデータを全件取得
		ResponseEntity<List<UserData>> responseList  = restTemplate.exchange(
				GET_ALL_USERS, HttpMethod.GET, new HttpEntity<>(httpHeaders)
				, new ParameterizedTypeReference<List<UserData>>() {});
		List<UserData> userDataList = responseList.getBody();
		printUserDataList(userDataList);
		log.debug("");
		
		log.debug("*** USER_DATAテーブルから特定のidをもつデータを取得した結果 ***");
		
		// user_dataテーブルで、id=1であるデータを取得
		ResponseEntity<UserData> response1 = restTemplate.exchange(
				GET_USER_1, HttpMethod.GET, new HttpEntity<>(httpHeaders)
				, UserData.class);
		UserData userData1 = response1.getBody();
		log.debug("id=1であるuser_dataテーブルの値: " + userData1);
		
		// user_dataテーブルで、id=10(存在しないデータ)を取得
		ResponseEntity<UserData> response10 = restTemplate.exchange(
				GET_USER_10, HttpMethod.GET, new HttpEntity<>(httpHeaders)
				, UserData.class);
		UserData userData10 = response10.getBody();
		log.debug("id=10であるuser_dataテーブルの値: " + userData10);
		log.debug("");
		
		log.debug("*** USER_DATAテーブルにid=4のデータを追加した結果 ***");
		
		// user_dataテーブルに、id=4であるデータを追加
		UserData newUserData = new UserData(4, "テスト　プリン４", 2014, 10, 11, "1", "テスト４");		
		ResponseEntity<UserData> responseAdd = restTemplate.exchange(
				POST_USER, HttpMethod.POST, new HttpEntity<>(newUserData, httpHeaders)
				, UserData.class);
		UserData addUser = responseAdd.getBody();
		log.debug("追加されたuser_dataテーブルの値: " + addUser);
		
		// user_dataテーブルで、追加されたデータを出力
		printUserData4();
		
		log.debug("*** USER_DATAテーブルのid=4のデータを更新した結果 ***");
		
		// user_dataテーブルの、id=4であるデータを更新
		UserData updUserData = new UserData(0, "テスト　プリン４更新後", 2015, 11, 12, "2", "テスト４更新後");		
		ResponseEntity<UserData> responseUpd = restTemplate.exchange(
				PUT_USER_4, HttpMethod.PUT, new HttpEntity<>(updUserData, httpHeaders)
				, UserData.class);
		UserData updUser = responseUpd.getBody();
		log.debug("更新されたuser_dataテーブルの値: " + updUser);
		
		// user_dataテーブルで、更新されたデータを出力
		printUserData4();
		
		log.debug("*** USER_DATAテーブルのid=4のデータを削除した結果 ***");
		
		// user_dataテーブルの、id=4であるデータを削除
		restTemplate.exchange(DELETE_USER_4, HttpMethod.DELETE
				, new HttpEntity<>(httpHeaders), Object.class);
		
		// user_dataテーブルで、削除されたデータを出力
		printUserData4();
		
		log.debug("*** USER_DATAテーブルから特定のidをもつデータを取得した結果(リクエストエラー時) ***");
		
		// user_dataテーブルで、id=a(HTTPエラーになるデータ)を取得
		try {
			restTemplate.exchange(GET_USER_a, HttpMethod.GET
					, new HttpEntity<>(httpHeaders), UserData.class);
		} catch(Exception ex) {
			log.error(ex);
		}
	}
	
	/**
	 * 引数で指定されたユーザーデータリストを出力する.
	 * @param userDataList ユーザーデータリスト
	 */
	private void printUserDataList(List<UserData> userDataList) {
		if(userDataList != null && !userDataList.isEmpty()) {
			for(UserData userData : userDataList) {
				log.debug(userData);
			}
		}
	}
	
	/**
	 * USER_DATAテーブルで追加・更新・削除されたデータを出力する.
	 */
	private void printUserData4() {
		ResponseEntity<UserData> response4 = restTemplate.exchange(
				GET_USER_4, HttpMethod.GET, new HttpEntity<>(httpHeaders)
				, UserData.class);
		UserData userData4 = response4.getBody();
		log.debug("id=4である、更新後のuser_dataテーブルの値: " + userData4);
		log.debug("");
	}

}
