package com.example.demo;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DemoCustomConfigration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        // HTTP 404(NOT FOUND)エラーが発生した場合に、パス「/notFoundNew」に遷移するよう設定
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notFoundNew"));
    }

}
