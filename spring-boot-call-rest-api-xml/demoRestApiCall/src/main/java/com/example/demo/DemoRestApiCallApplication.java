package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoRestApiCallApplication implements CommandLineRunner {

	/** Rest APIでXML形式/JSON形式でデータ取得する処理を呼び出すサービスクラス */
	@Autowired
	private DemoRestApiCallXmlService demoRestApiCallXmlService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoRestApiCallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Rest APIでXML形式/JSON形式でデータ取得する処理を呼び出す
		demoRestApiCallXmlService.execRestApiXml();
	}

}
