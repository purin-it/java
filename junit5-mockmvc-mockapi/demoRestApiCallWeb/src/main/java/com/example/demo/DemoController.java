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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class DemoController {

	/** RestTemplateオブジェクト */
    @Autowired
    private RestTemplate restTemplate;
    
    /** HttpHeadersオブジェクト */
	@Autowired
	private HttpHeaders httpHeaders;
	
	/** ログ出力のためのクラス */
	private static Log log = LogFactory.getLog(DemoController.class);
    
    /**
     * ユーザーデータを取得し、初期表示画面に遷移する
     * @param model Modelオブジェクト
     * @return 初期表示画面へのパス
     */
	@RequestMapping("/")
	public String index(Model model) {
		// ユーザーデータリストをAPIで取得し、Modelオブジェクトに設定する
        ResponseEntity<List<UserData>> response = restTemplate.exchange(
                "http://localhost:8085/users", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<UserData>>() {});    
        model.addAttribute("userDataList", response.getBody());
        
		return "index";
	}
	
	/**
	 * ユーザー登録画面に遷移する
	 * @param model Modelオブジェクト
	 * @return ユーザー登録画面へのパス
	 */
	@PostMapping("/toAdd")
	public String toAdd(Model model) {
		model.addAttribute("demoForm", new DemoForm());
		return "add";
	}
	
	/**
	 * ユーザー登録を行い、初期表示画面に遷移する
	 * @param demoForm Formオブジェクト
	 * @param model Modelオブジェクト
	 * @return 初期表示画面に遷移する処理
	 */
	@PostMapping(value = "/add", params = "next")
    public String add(DemoForm demoForm, Model model){
		try {
			// ユーザー登録処理を行う
			UserData newUserData = getAddUserData(demoForm);
			restTemplate.exchange(
					"http://localhost:8085/users", HttpMethod.POST
					, new HttpEntity<>(newUserData, httpHeaders)
					, UserData.class);
		} catch(Exception ex) {
			log.error(ex);
			model.addAttribute("message", "エラーが発生しました。");
			return toAdd(model);
		}
		
        // 初期表示画面に遷移
        return index(model);
    }
	
	/**
	 * ユーザー登録画面から、初期表示画面に戻る
	 * @param model Modelオブジェクト
	 * @return 初期表示画面に戻る処理
	 */
	@PostMapping(value = "/add", params = "back")
    public String toIndex(Model model){
        // 初期表示画面に戻る
        return index(model);
    }
	
	/**
	 * 引数のフォームから、戻り値ユーザーデータの値を生成する
	 * @param demoForm　Formオブジェクト
	 * @return　ユーザーデータ
	 */
	private UserData getAddUserData(DemoForm demoForm) {
		UserData userData = new UserData();
		userData.setId(Long.valueOf(demoForm.getId()));
		userData.setName(demoForm.getName());
		userData.setBirthY(Integer.valueOf(demoForm.getBirthY()));
		userData.setBirthM(Integer.valueOf(demoForm.getBirthM()));
		userData.setBirthD(Integer.valueOf(demoForm.getBirthD()));
		userData.setSex(demoForm.getSex());
		userData.setMemo(demoForm.getMemo());
		return userData;
	}
	
}
