package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoRestApiCallApplication implements CommandLineRunner {

	/** ユーザーデータを追加し、追加したデータを取得するURLも返却する処理の呼び出しを実行するサービスクラス */
	@Autowired
	private DemoRestApiHateoasService demoRestApiHateoasService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoRestApiCallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// ユーザーデータを追加し、追加したデータを取得するURLも返却する処理を呼び出す
		demoRestApiHateoasService.execRestApiHateoas();
	}

}
