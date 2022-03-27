package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 「@Data」アノテーションを付与すると、このクラス内の全フィールドに対する
// Getterメソッド・Setterメソッドにアクセスができる
// 「@NoArgsConstructor」は、引数をもたないコンストラクタを生成するアノテーション
// 「@AllArgsConstructor」は、全てのメンバを引数にもつコンストラクタを生成するアノテーション
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

	/** ID */
    private long id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    private int birthY;

    /** 生年月日_月 */
    private int birthM;

    /** 生年月日_日 */
    private int birthD;

    /** 性別 */
    private String sex;

    /** メモ */
    private String memo;
    
}
