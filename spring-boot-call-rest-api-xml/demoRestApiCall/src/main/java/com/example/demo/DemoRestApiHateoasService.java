package com.example.demo;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoRestApiHateoasService {

	/** ユーザーデータを追加し、追加したデータを取得するURLも返却可能となるURL */
	private static final String POST_USER_HATEOAS = "http://localhost:8085/users/hateoas";
	
	/** RestTemplateオブジェクト */
	@Autowired
	private RestTemplate restTemplate;
	
	/** HttpHeadersオブジェクト */
	@Autowired
	private HttpHeaders httpHeaders;
	
	/** ログ出力のためのクラス */
	private static Log log = LogFactory.getLog(DemoRestApiHateoasService.class);
	
	public void execRestApiHateoas() {
		
		log.debug("*** USER_DATAテーブルにid=4のデータを追加し、追加したデータを取得するURLも返却した結果 ***");
		
		// user_dataテーブルに、id=4であるデータを追加
		UserData newUserData = new UserData(4, "テスト　プリン４", 2014, 10, 11, "1", "テスト４");		
		ResponseEntity<EntityModel<UserData>> response = restTemplate.exchange(
				POST_USER_HATEOAS, HttpMethod.POST, new HttpEntity<>(newUserData, httpHeaders)
				, new ParameterizedTypeReference<EntityModel<UserData>>() {});
		
		// 戻り値を「EntityModel<UserData>」型で取得
		EntityModel<UserData> entityModel = response.getBody();
		
		// user_dataテーブルで、追加されたデータを出力
		UserData addUserData = entityModel.getContent();
		log.debug("追加されたuser_dataテーブルの値: " + addUserData);
		
		// 追加したデータを取得するURLを出力
		Optional<Link> optLink = entityModel.getLink("getAddUserDataUrl");
		if(optLink.isPresent()) {
			String getAddUserDataUrl = optLink.get().toUri().toString();
			log.debug("追加したデータを取得するURL : " + getAddUserDataUrl);
		}
		
	}

}
