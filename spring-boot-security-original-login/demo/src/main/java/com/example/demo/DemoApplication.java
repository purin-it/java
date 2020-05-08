package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

	@Autowired
	private MessageSource messageSource;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		//Log4j2でログ出力を行う
		Logger logger = LogManager.getLogger(DemoApplication.class);
		logger.debug("これはDEBUG(デバッグ)のテスト用ログです");
		logger.info("これはINFO(情報)のテスト用ログです");
		logger.warn("これはWARN(警告)のテスト用ログです");
		logger.error("これはERROR(エラー)のテスト用ログです。");
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		//Spring Bootデフォルトのエラーメッセージのプロパティファイルを
		//ValidationMessages.propertiesからmessages.propertiesに変更する
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}

	@Override
	public org.springframework.validation.Validator getValidator() {
		return validator();
	}
}
