package com.enotes.api.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("mahi tech").apiInfo(apiInfo()).select()
                .paths(regex("/student.*")).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Student Management")
                .description("Sample documentation")
                .termsOfServiceUrl("https://test.com")
                .license("Student service")
                .licenseUrl("https://www.mahitech.com")
                .version("1.0")
                .build();
    }
}
