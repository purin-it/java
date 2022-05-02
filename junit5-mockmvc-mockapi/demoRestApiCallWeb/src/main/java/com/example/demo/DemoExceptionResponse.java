package com.example.demo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 例外処理を行うハンドラークラス内で返却するレスポンスエンティティに指定するクラス
 */
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