package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
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
     * ユーザーデータテーブル(user_data)へアクセスするリポジトリ
     */
    @Autowired
    private UserDataRepository repository;

    /**
     * ユーザーデータを全件取得する
     * @return ユーザーデータリスト
     */
    @PostMapping("/getUserDataList")
    public List<UserData> getUserDataList(){
        List<UserData> userDataList = repository.findAll(new Sort(ASC, "id"));
        // ユーザーデータが取得できなかった場合は、null値を返す
        if(userDataList == null || userDataList.size() == 0){
            return null;
        }
        for(UserData userData : userDataList){
            // 性別を表示用(男,女)に変換
            userData.setSex("1".equals(userData.getSex()) ? "男" : "女");
            // 名前・メモ・性別を暗号化
            userData.setName(encrypt(userData.getName()));
            userData.setMemo(encrypt(userData.getMemo()));
            userData.setSex(encrypt(userData.getSex()));
        }
        // 取得したユーザーデータを返却
        return userDataList;
    }

    /**
     * 引数の文字列を暗号化する
     * @param data 任意の文字列
     * @return 暗号化後の文字列
     */
    private String encrypt(String data) {
        if(StringUtils.isEmpty(data)){
            return "";
        }
        String retVal = null;
        try {
            // 暗号化キーオブジェクトを生成
            SecretKeySpec key = new SecretKeySpec(
                    encrypyKey.getBytes(encryptCharset), encryptAlgorithm);
            // Cipherオブジェクトを生成し、暗号化キーオブジェクトで初期化
            // 暗号化キーは、鍵長を16バイト・24バイト・32バイトのいずれかに設定する必要がある
            Cipher cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 引数の文字列を暗号化
            byte[] byteResult = cipher.doFinal(data.getBytes(encryptCharset));
            // Base64へエンコードし、暗号化文字列を返却
            retVal = Base64.encodeBase64String(byteResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

}
