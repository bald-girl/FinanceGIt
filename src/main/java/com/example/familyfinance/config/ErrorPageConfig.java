package com.example.familyfinance.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error403Page=new ErrorPage(HttpStatus.FORBIDDEN,"/403");
        registry.addErrorPages(error403Page);
    }
}
