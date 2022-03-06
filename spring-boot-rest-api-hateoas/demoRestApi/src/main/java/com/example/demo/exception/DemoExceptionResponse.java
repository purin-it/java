package com.example.demo.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 例外処理を行うハンドラークラス内で返却するレスポンスエンティティに指定するクラス
 */
//「@Data」アノテーションを付与すると、このクラス内の全フィールドに対する
//Getterメソッド・Setterメソッドにアクセスができる
//「@NoArgsConstructor」は、引数をもたないコンストラクタを生成するアノテーション
//「@AllArgsConstructor」は、全てのメンバを引数にもつコンストラクタを生成するアノテーション
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoExceptionResponse {
	
	/** メッセージ */
	private String message;
	
	/** メッセージ詳細 */
	private String details;
	
	/** 発生日時 */
	private Date timestamp;

}