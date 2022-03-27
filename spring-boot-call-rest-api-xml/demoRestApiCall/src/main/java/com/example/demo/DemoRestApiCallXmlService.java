package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoRestApiCallXmlService {

	/** 全てのユーザーデータを取得するURL */
	private static final String GET_ALL_USERS = "http://localhost:8085/users";

	/** ユーザーデータを追加するURL */
	private static final String POST_USER = "http://localhost:8085/users";

	/** RestTemplateオブジェクト */
	@Autowired
	private RestTemplate restTemplate;

	/** HttpHeadersオブジェクト */
	@Autowired
	private HttpHeaders httpHeaders;

	/** ログ出力のためのクラス */
	private static Log log = LogFactory.getLog(DemoRestApiCallXmlService.class);

	/**
	 * Rest APIでXML形式/JSON形式でデータ取得する処理の呼び出しを実行する.
	 */
	public void execRestApiXml() {
		// Headersに「Accept: application/xml」を指定
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

		// Headersに「Accept: application/xml」を指定し、戻り値を文字列で取得
		ResponseEntity<String> responseXml1 = restTemplate.exchange(GET_ALL_USERS, HttpMethod.GET,
				new HttpEntity<>(httpHeaders), String.class);
		String resBodyXml1 = responseXml1.getBody();
		log.debug("USER_DATAテーブルから全データを取得した結果(Accept: application/xml、戻り値: 文字列) : ");
		log.debug(resBodyXml1);
		log.debug("");

		// Headersに「Accept: application/xml」を指定し、戻り値をUserDataオブジェクトのリストで取得
		ResponseEntity<List<UserData>> responseList = restTemplate.exchange(GET_ALL_USERS, HttpMethod.GET,
				new HttpEntity<>(httpHeaders), new ParameterizedTypeReference<List<UserData>>() {
				});
		List<UserData> userDataList = responseList.getBody();
		log.debug("USER_DATAテーブルから全データを取得した結果(Accept: application/xml、戻り値: UserDataオブジェクトのリスト) : ");
		printUserDataList(userDataList);
		log.debug("");

		// Headersに「Accept: application/json」を指定
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		// Headersに「Accept: application/json」を指定し、戻り値を文字列で取得
		ResponseEntity<String> responseJson1 = restTemplate.exchange(GET_ALL_USERS, HttpMethod.GET,
				new HttpEntity<>(httpHeaders), String.class);
		String resBodyJson1 = responseJson1.getBody();
		log.debug("USER_DATAテーブルから全データを取得した結果(Accept: application/json、戻り値: 文字列) : ");
		log.debug(resBodyJson1);
		log.debug("");

		// Headersに「Accept: application/json」を指定し、戻り値をUserDataオブジェクトのリストで取得
		ResponseEntity<List<UserData>> responseJson2 = restTemplate.exchange(GET_ALL_USERS, HttpMethod.GET,
				new HttpEntity<>(httpHeaders), new ParameterizedTypeReference<List<UserData>>() {
				});
		List<UserData> resBodyJson2 = responseJson2.getBody();
		log.debug("USER_DATAテーブルから全データを取得した結果(Accept: application/json、戻り値: UserDataオブジェクトのリスト) : ");
		printUserDataList(resBodyJson2);
		log.debug("");

		// Headersに「Accept: */*」を指定
		httpHeaders.setAccept(Arrays.asList(MediaType.ALL));

		// Headersに「Accept: */*」を指定し、戻り値を文字列で取得
		ResponseEntity<String> responseAll1 = restTemplate.exchange(GET_ALL_USERS, HttpMethod.GET,
				new HttpEntity<>(httpHeaders), String.class);
		String resBodyAll1 = responseAll1.getBody();
		log.debug("USER_DATAテーブルから全データを取得した結果(Accept: */*、戻り値: UserDataオブジェクトのリスト) : ");
		log.debug(resBodyAll1);
		log.debug("");

		// Headersに「Accept: */*」を指定し、戻り値をUserDataオブジェクトのリストで取得
		ResponseEntity<List<UserData>> responseAll2 = restTemplate.exchange(GET_ALL_USERS, HttpMethod.GET,
				new HttpEntity<>(httpHeaders), new ParameterizedTypeReference<List<UserData>>() {
				});
		List<UserData> resBodyAll2 = responseAll2.getBody();
		log.debug("USER_DATAテーブルから全データを取得した結果(Accept: */*、戻り値: UserDataオブジェクトのリスト) : ");
		printUserDataList(resBodyAll2);
		log.debug("");

		// Headersに「Accept: application/xml」「Content-Type:application/xml」を指定し、戻り値を文字列で取得
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		httpHeaders.setContentType(MediaType.APPLICATION_XML);

		// user_dataテーブルに、id=4であるデータを追加
		UserData newUserData1 = new UserData(4, "テスト　プリン４", 2003, 1, 25, "2", "テスト４");
		ResponseEntity<String> responseAdd1 = restTemplate.exchange(POST_USER, HttpMethod.POST,
				new HttpEntity<>(newUserData1, httpHeaders), String.class);
		String resBodyAdd1 = responseAdd1.getBody();
		log.debug("USER_DATAテーブルにデータを追加した結果(Accept: application/xml、Content-Type: application/xml、戻り値: 文字列) : ");
		log.debug(resBodyAdd1);
		log.debug("");

		// Headersに「Accept: application/xml」「Content-Type:application/json」を指定し、戻り値を文字列で取得
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		// user_dataテーブルに、id=5であるデータを追加
		UserData newUserData2 = new UserData(5, "テスト　プリン５", 2008, 6, 11, "1", "テスト５");
		ResponseEntity<String> responseAdd2 = restTemplate.exchange(POST_USER, HttpMethod.POST,
				new HttpEntity<>(newUserData2, httpHeaders), String.class);
		String resBodyAdd2 = responseAdd2.getBody();
		log.debug("USER_DATAテーブルにデータを追加した結果(Accept: application/xml、Content-Type: application/json、戻り値: 文字列) : ");
		log.debug(resBodyAdd2);
		log.debug("");

		// Headersに「Accept: application/json」「Content-Type:application/json」を指定し、戻り値を文字列で取得
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		// user_dataテーブルに、id=6であるデータを追加
		UserData newUserData3 = new UserData(6, "テスト　プリン６", 2012, 12, 3, "2", "テスト６");
		ResponseEntity<String> responseAdd3 = restTemplate.exchange(POST_USER, HttpMethod.POST,
				new HttpEntity<>(newUserData3, httpHeaders), String.class);
		String resBodyAdd3 = responseAdd3.getBody();
		log.debug("USER_DATAテーブルにデータを追加した結果(Accept: application/json、Content-Type: application/json、戻り値: 文字列) : ");
		log.debug(resBodyAdd3);
		log.debug("");

		// Headersに「Accept: */*」「Content-Type: application/xml」を指定し、戻り値を文字列で取得
		httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
		httpHeaders.setContentType(MediaType.APPLICATION_XML);

		// user_dataテーブルに、id=7であるデータを追加
		UserData newUserData4 = new UserData(7, "テスト　プリン７", 2001, 7, 21, "1", "テスト７");
		ResponseEntity<String> responseAdd4 = restTemplate.exchange(POST_USER, HttpMethod.POST,
				new HttpEntity<>(newUserData4, httpHeaders), String.class);
		String resBodyAdd4 = responseAdd4.getBody();
		log.debug("USER_DATAテーブルにデータを追加した結果(Accept: */*、Content-Type: application/xml、戻り値: 文字列) : ");
		log.debug(resBodyAdd4);
		log.debug("");
	}

	/**
	 * 引数で指定されたユーザーデータリストを出力する.
	 * 
	 * @param userDataList ユーザーデータリスト
	 */
	private void printUserDataList(List<UserData> userDataList) {
		if (userDataList != null && !userDataList.isEmpty()) {
			for (UserData userData : userDataList) {
				log.debug(userData);
			}
		}
	}
}
