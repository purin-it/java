package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoRestApiCallApplication implements CommandLineRunner {

	/** Rest APIでデータ追加・更新時にチェックエラーになる処理の呼び出しを実行するサービスクラス */
	@Autowired
	private DemoRestApiCallCheckService demoRestApiCallCheckService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoRestApiCallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Rest APIでデータ追加・更新時にチェックエラーになる処理を呼び出すメソッドを呼び出す
		demoRestApiCallCheckService.execRestApiCheck();
	}

}
