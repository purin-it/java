package com.example.demo.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 例外処理を行うハンドラークラス
 */
@ControllerAdvice
@RestController
public class DemoExceptionHandler {
	
	/**
	 * APIのURLが不正な場合のエラー処理を行う.
	 * @param ex MethodArgumentTypeMismatch例外
	 * @return レスポンスエンティティオブジェクト
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<DemoExceptionResponse> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex){
		DemoExceptionResponse exceptionResponse = new DemoExceptionResponse(
				ex.getMessage(), getStackTraceLog(ex.getStackTrace()), new Date());
		return new ResponseEntity<DemoExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * UserDataクラスのチェック処理でエラーになった場合の処理を行う.
	 * @param ex MethodArgumentNotValid例外
	 * @return レスポンスエンティティオブジェクト
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DemoExceptionResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex){
		DemoExceptionResponse exceptionResponse = new DemoExceptionResponse(
				getMethodArgumentNotValidExceptionMsg(ex), ex.getMessage(), new Date());
		return new ResponseEntity<DemoExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * その他例外が発生した場合の処理を行う.
	 * @param ex 任意例外
	 * @return レスポンスエンティティオブジェクト
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<DemoExceptionResponse> handleException(Exception ex){
		DemoExceptionResponse exceptionResponse = new DemoExceptionResponse(
				ex.getMessage(), getStackTraceLog(ex.getStackTrace()), new Date());
		return new ResponseEntity<DemoExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * スタックトレースログの文字列を取得する.
	 * @param stackArray StackTraceElement配列
	 * @return スタックトレースログの文字列
	 */
	private String getStackTraceLog(StackTraceElement[] stackArray) {
		StringBuilder detail = new StringBuilder();
		for(StackTraceElement stackTraceElement : stackArray) {
			detail.append(stackTraceElement);
		}
		return detail.toString();
	}
	
	/**
	 * MethodArgumentNotValid例外からエラーメッセージを生成する
	 * @param ex MethodArgumentNotValid例外
	 * @return エラーメッセージ
	 */
	private String getMethodArgumentNotValidExceptionMsg(MethodArgumentNotValidException ex) {
		StringBuilder errMsg = new StringBuilder();
		if(ex.getFieldErrorCount() > 0) {
			List<FieldError> fieldErrorList = ex.getFieldErrors();
			for(FieldError error : fieldErrorList) {
				errMsg.append(error.getDefaultMessage());
			}
		}
		if(ex.getGlobalErrorCount() > 0) {
			List<ObjectError> objectErrorList = ex.getGlobalErrors();
			for(ObjectError error : objectErrorList) {
				errMsg.append(error.getDefaultMessage());
			}
		}
		return errMsg.toString();
	}
}
