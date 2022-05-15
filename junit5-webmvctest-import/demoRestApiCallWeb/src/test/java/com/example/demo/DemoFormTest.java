package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@SpringBootTest
public class DemoFormTest {

	/** Validatorオブジェクト */
	@Autowired
	private Validator validator;

	/** 検証結果を設定するBindingResult */
	private BindingResult bindingResult;

	/**
	 * 各テストメソッドを実行する前に実行する処理
	 */
	@BeforeEach
	public void setUp() {
		bindingResult = new BindException(new DemoForm(), "demoForm");
	}

	/**
	 * Formオブジェクト(エラー無し)の検証を行うテストを実行する
	 */
	@Test
	public void testHasNoError() {
		System.out.println("*** testHasNoErrorメソッド　開始 ***");

		// テスト対象となる検証対象のFormオブジェクト(エラー無し)を生成
		DemoForm demoForm = new DemoForm("3", "テスト　プリン３", "2013", "2", "1", "1", "テスト３");
		System.out.println("demoForm : " + demoForm);

		// 生成したFormオブジェクトを検証
		validator.validate(demoForm, bindingResult);

		// Formオブジェクトの検証結果を確認
		assertFalse(bindingResult.hasErrors());
		System.out.println("bindingResult.hasErrors()の値 : false");

		System.out.println("*** testHasNoErrorメソッド　終了 ***");
	}

	/**
	 * Formオブジェクト(IDでエラー)の検証を行うテストを実行する
	 */
	@Test
	public void testHasIdError() {
		System.out.println("*** testHasIdErrorメソッド　開始 ***");

		DemoForm demoForm = new DemoForm("3a", "テスト　プリン３", "2013", "2", "1", "1", "テスト３");
		System.out.println("demoForm : " + demoForm);

		validator.validate(demoForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("{0}は1以上の数値で入力してください。", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("id", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : " + bindingResult.getFieldError().toString());

		System.out.println("*** testHasIdErrorメソッド　終了 ***");
	}

	/**
	 * Formオブジェクト(名前でエラー)の検証を行うテストを実行する
	 */
	@Test
	public void testHasNameError() {
		System.out.println("*** testHasNameErrorメソッド　開始 ***");

		DemoForm demoForm = new DemoForm("3", "", "2013", "2", "1", "1", "テスト３");
		System.out.println("demoForm : " + demoForm);

		validator.validate(demoForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("{0}を入力してください。", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("name", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : " + bindingResult.getFieldError().toString());

		System.out.println("*** testHasNameErrorメソッド　終了 ***");
	}

	/**
	 * Formオブジェクト(生年月日でエラー)の検証を行うテストを実行する
	 */
	@Test
	public void testHasBirthdayError() {
		System.out.println("*** testHasBirthdayErrorメソッド　開始 ***");

		DemoForm demoForm = new DemoForm("3", "テスト　プリン３", "2013", "2", "31", "1", "テスト３");
		System.out.println("demoForm : " + demoForm);

		validator.validate(demoForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("生年月日が存在しない日付になっています。", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("birthY", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : " + bindingResult.getFieldError().toString());

		System.out.println("*** testHasBirthdayErrorメソッド　終了 ***");
	}

	/**
	 * Formオブジェクト(生年月日未入力でエラー)の検証を行うテストを実行する
	 */
	@Test
	public void testHasNoBirthdayError() {
		System.out.println("*** testHasNoBirthdayErrorメソッド　開始 ***");

		DemoForm demoForm = new DemoForm("3", "テスト　プリン３", "", "", "", "1", "テスト３");
		System.out.println("demoForm : " + demoForm);

		validator.validate(demoForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("{0}を入力してください。", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("birthDayRequired", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : " + bindingResult.getFieldError().toString());

		System.out.println("*** testHasNoBirthdayErrorメソッド　終了 ***");
	}

	/**
	 * Formオブジェクト(性別でエラー)の検証を行うテストを実行する
	 */
	@Test
	public void testHasSexError() {
		System.out.println("*** testHasSexErrorメソッド　開始 ***");

		DemoForm demoForm = new DemoForm("3", "テスト　プリン３", "2013", "2", "1", "3", "テスト３");
		System.out.println("demoForm : " + demoForm);

		validator.validate(demoForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("性別に不正な値が入っています。", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("sexInvalid", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : " + bindingResult.getFieldError().toString());

		System.out.println("*** testHasSexErrorメソッド　終了 ***");
	}

	/**
	 * Formオブジェクト(複数箇所でエラー)の検証を行うテストを実行する
	 */
	@Test
	public void testMultiError() {
		System.out.println("*** testMultiErrorメソッド　開始 ***");

		DemoForm demoForm = new DemoForm("3a", "", "2013", "2", "31", "3", "テスト３");
		System.out.println("demoForm : " + demoForm);

		validator.validate(demoForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals(4, bindingResult.getFieldErrors().size());
		assertEquals("{0}は1以上の数値で入力してください。", bindingResult.getFieldErrors("id").get(0).getDefaultMessage());
		assertEquals("{0}を入力してください。", bindingResult.getFieldErrors("name").get(0).getDefaultMessage());
		assertEquals("生年月日が存在しない日付になっています。", bindingResult.getFieldErrors("birthY").get(0).getDefaultMessage());
		assertEquals("性別に不正な値が入っています。", bindingResult.getFieldErrors("sexInvalid").get(0).getDefaultMessage());

		System.out.println("エラー数: 4");
		System.out.println("bindingResultの設定値 : " + bindingResult.getAllErrors());

		System.out.println("*** testMultiErrorメソッド　終了 ***");
	}

}
