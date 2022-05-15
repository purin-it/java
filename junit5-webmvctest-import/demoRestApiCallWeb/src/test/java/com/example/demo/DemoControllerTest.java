package com.example.demo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

// @SpringBootTestアノテーションの代わりに、@WebMvcTestアノテーションを利用
//　@Importアノテーションで、テスト対象クラスで参照するクラスをDIできるように指定
@WebMvcTest(controllers = DemoController.class)
@Import({RestTemplate.class, HttpHeaders.class})
public class DemoControllerTest {

	/** MockMvcオブジェクト */
	// @WebMvcTestアノテーションにより、このアノテーションの属性に指定したコントローラクラスを
	// テスト対象クラスに設定したMockMvcオブジェクトが、自動生成される
	@Autowired
	private MockMvc mockMvc;

	/** MockServerオブジェクト */
	private MockRestServiceServer mockServer;

	/** テスト対象となるコントローラクラスで呼ばれるRestTemplateオブジェクト */
	@Autowired
	private RestTemplate restTemplate;

	/** JSON文字列とObjectの変換を行うオブジェクト */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 各テストメソッドを実行する前に実行する処理
	 */
	@BeforeEach
	public void setUp() {
		// テスト対象となるコントローラクラスで呼ばれるRestTemplateにMockサーバーを割り当てる
		mockServer = MockRestServiceServer
				.bindTo(restTemplate)
				.build();
	}
	
	/**
	 * DemoControllerクラスのindexメソッドのテストを実行する
	 * @throws Exception 任意例外
	 */
	@Test
	public void testIndex() throws Exception {
		String mockServerRes = objectMapper.writeValueAsString(getUserDataList());
		List<UserData> userDataList = getUserDataList();

		System.out.println("*** testIndexメソッド　開始 ***");

		// テスト対象となるコントローラクラスのindexメソッドで呼ばれるAPI実行時のモック設定をする
		// APIのURLを設定
		mockServer.expect(requestTo("http://localhost:8085/users"))
				// HTTPメソッドをGETに設定
				.andExpect(method(HttpMethod.GET))
				// APIの戻り値を設定
				.andRespond(withSuccess(mockServerRes, MediaType.APPLICATION_JSON));

		System.out.println("API実行後の戻り値 : " + mockServerRes);

		// テスト対象メソッド(index)を実行
		mockMvc.perform(get("/"))
				// HTTPステータスがOKであることを確認
				.andExpect(status().isOk())
				// 次画面の遷移先がindex.htmlであることを確認
				.andExpect(view().name("index"))
				// Modelオブジェクトにエラーが無いことを確認
				.andExpect(model().hasNoErrors())
				// Modelオブジェクトの設定値を確認
				.andExpect(model().attribute("userDataList", userDataList));

		System.out.println("Modelオブジェクト、userDataListの設定値 : " + userDataList);
		System.out.println("*** testIndexメソッド　終了 ***");
	}
	
	/**
	 * testIndexメソッドで利用するユーザーデータリストを返却する
	 * @return ユーザーデータリスト
	 */
	private List<UserData> getUserDataList() {
		List<UserData> userDataList = new ArrayList<>();
		userDataList.add(new UserData(1, "テスト　プリン１", 2012, 3, 12, "1", "テスト１"));
		userDataList.add(new UserData(2, "テスト　プリン２", 2013, 1, 7, "2", "テスト２"));
		return userDataList;
	}

	/**
	 * DemoControllerクラスのaddメソッド(正常時)のテストを実行する
	 * @throws Exception 任意例外
	 */
	@Test
	public void testAddNormal() throws Exception {
		UserData userData = new UserData(3, "テスト　プリン３", 2010, 8, 31, "2", "テスト３");
		String jsonUserData = objectMapper.writeValueAsString(userData);

		System.out.println("*** testAddNormalメソッド　開始 ***");

		// テスト対象となるコントローラクラスのaddメソッドで呼ばれるAPI実行時のモック設定をする
		mockServer.expect(requestTo("http://localhost:8085/users"))
				// HTTPメソッドをPOSTに設定
				.andExpect(method(HttpMethod.POST))
				// HTTP HeaderのContent-Typeがapplication/jsonであることを確認
				.andExpect(header("Content-Type", "application/json"))
				// リクエストボディの設定値を確認
				.andExpect(content().string(jsonUserData))
				// APIの戻り値を設定
				.andRespond(withSuccess(jsonUserData, MediaType.APPLICATION_JSON));

		System.out.println("API実行時のリクエストボディ : " + jsonUserData);
		System.out.println("API実行後の戻り値 : " + jsonUserData);

		String mockServerRes = objectMapper.writeValueAsString(getUserDataList2());
		List<UserData> userDataList2 = getUserDataList2();

		// テスト対象となるコントローラクラスのindexメソッドで呼ばれるAPI実行時のモック設定をする
		mockServer.expect(requestTo("http://localhost:8085/users"))
				// HTTPメソッドをGETに設定
				.andExpect(method(HttpMethod.GET))
				// APIの戻り値を設定
				.andRespond(withSuccess(mockServerRes, MediaType.APPLICATION_JSON));

		// テスト対象メソッド(add)を実行
		DemoForm demoForm = new DemoForm("3", "テスト　プリン３", "2010", "8", "31", "2", "テスト３");
		mockMvc.perform(MockMvcRequestBuilders.post("/add")
				// paramsに設定する値を指定
				.param("next", "next")
				// formオブジェクトを設定
				.flashAttr("demoForm", demoForm))
				// HTTPステータスがOKであることを確認
				.andExpect(status().isOk())
				// 次画面の遷移先がindex.htmlであることを確認
				.andExpect(view().name("index"))
				// Modelオブジェクトにエラーが無いことを確認
				.andExpect(model().hasNoErrors())
				// Modelオブジェクトの設定値を確認
				.andExpect(model().attribute("userDataList", userDataList2));

		System.out.println("formオブジェクトの設定値 : " + demoForm);
		System.out.println("Modelオブジェクト、userDataListの設定値 : " + userDataList2);
		System.out.println("*** testAddNormalメソッド　終了 ***");
	}
	
	/**
	 * testAddNormalメソッドで利用するユーザーデータリストを返却する
	 * @return ユーザーデータリスト
	 */
	private List<UserData> getUserDataList2() {
		List<UserData> userDataList = new ArrayList<>();
		userDataList.add(new UserData(1, "テスト　プリン１", 2012, 3, 12, "1", "テスト１"));
		userDataList.add(new UserData(2, "テスト　プリン２", 2013, 1, 7, "2", "テスト２"));
		userDataList.add(new UserData(3, "テスト　プリン３", 2010, 8, 31, "2", "テスト３"));
		return userDataList;
	}

	/**
	 * DemoControllerクラスのaddメソッド(異常時)のテストを実行する
	 * @throws Exception 任意例外
	 */
	@Test
	public void testAddException() throws Exception {
		System.out.println("*** testAddExceptionメソッド　開始 ***");
		
		// テスト対象メソッド(add)を実行
		DemoForm demoForm = new DemoForm("3", "テスト　プリン３"
				, "2010", "2", "31", "2", "テスト３");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/add")
				// paramsに設定する値を指定
				.param("next", "next")
				// formオブジェクトを設定
				.flashAttr("demoForm", demoForm))
				// HTTPステータスがOKであることを確認
				.andExpect(status().isOk())
				// 次画面の遷移先がadd.htmlであることを確認
				.andExpect(view().name("add"))
				// Modelオブジェクトにチェックエラー内容が設定されたことを確認
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode(
						"demoForm", "birthY", "CheckDate"));

		System.out.println("formオブジェクトの設定値 : " + demoForm);
		System.out.println("*** testAddExceptionメソッド　終了 ***");
	}

}
