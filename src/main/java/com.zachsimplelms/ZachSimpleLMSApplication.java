package com.zachsimplelms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
public class ZachSimpleLMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZachSimpleLMSApplication.class, args);
    }

    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_12)
                .select()
                .paths(PathSelectors.ant("/zachsimplelms/*"))
                .apis(RequestHandlerSelectors.basePackage("com.zachsimplelms"))
                .build()
                .apiInfo(apiZachSimpleLMSData());
    }

    private ApiInfo apiZachSimpleLMSData(){
        return new ApiInfo(
                "Zach's Simple Library Management System",
                "This is a TEST REST API Application for Zach Simple LMS (Library Management System)!",
                "1.0",
                "ZachSimpleLMS",
                new Contact("Zachary","www.zachsimplelms.com","zachchoong21@gmail.com"),
                "Zach Simple LMS License",
                "www.ZachSimpleLMSLicensing.com",
                Collections.emptyList()
        );
    }

}
