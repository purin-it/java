package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Controller
public class DemoController {

    /**
     * 暗号化アルゴリズム
     */
    @Value("${encrypt.algorithm}")
    private String encryptAlgorithm;

    /**
     * 暗号化時に利用する文字コード
     */
    @Value("${encrypt.charset}")
    private String encryptCharset;

    /**
     * 暗号化時に利用する秘密鍵
     */
    @Value("${encrypt.key}")
    private String encrypyKey;

    /**
     * RestTemplateオブジェクト
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * ユーザーデータを取得し、初期表示画面に遷移する
     * @param model Modelオブジェクト
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(Model model){
        // ユーザーデータリストをAPIで取得する
        ResponseEntity<List<UserData>> response = restTemplate.exchange(
                "http://localhost:8085/getUserDataList", HttpMethod.POST,
                null, new ParameterizedTypeReference<List<UserData>>() {});
        List<UserData> userDataList = response.getBody();
        // 各ユーザーデータを編集し、Modelに設定する
        for(UserData userData : userDataList){
            // 名前・メモ・性別・生年月日を復号化
            userData.setName(decrypt(userData.getName()));
            userData.setMemo(decrypt(userData.getMemo()));
            userData.setSex(decrypt(userData.getSex()));
        }
        model.addAttribute("userDataList", userDataList);
        return "index";
    }

    /**
     * 引数の文字列を復号化する
     * @param data 任意の文字列
     * @return 復号化後の文字列
     */
    private String decrypt(String data){
        if(StringUtils.isEmpty(data)){
            return "";
        }
        String retVal = null;
        try {
            // 引数の文字列をBase64へデコード
            byte[] byteText = Base64.decodeBase64(data);
            // 暗号化キーオブジェクトを生成
            SecretKeySpec key = new SecretKeySpec(
                    encrypyKey.getBytes(encryptCharset), encryptAlgorithm);
            // Cipherオブジェクトを生成し、暗号化キーオブジェクトで初期化
            // 暗号化キーは、鍵長を16バイト・24バイト・32バイトのいずれかに設定する必要がある
            Cipher cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 引数の文字列を復号化
            byte[] byteResult = cipher.doFinal(byteText);
            // 復号化後の文字列を返却
            retVal = new String(byteResult, encryptCharset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
