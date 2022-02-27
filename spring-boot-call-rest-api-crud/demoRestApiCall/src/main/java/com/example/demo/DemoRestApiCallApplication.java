package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoRestApiCallApplication implements CommandLineRunner {

	/** Rest APIの各種呼び出しを実行するサービスクラス */
	@Autowired
	private DemoRestApiCallService demoRestApiCallService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoRestApiCallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Rest APIの各種呼び出しを実行するメソッドを呼び出す
		demoRestApiCallService.execRestApi();
	}

}
