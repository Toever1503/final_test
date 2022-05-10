package com.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/api/.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("cy API")
                .description("cy API reference for developers")
                .termsOfServiceUrl("/http://aladintech.co")
                .contact("haunv@cy.co").license("cy cy")
                .licenseUrl("/cy.co").version("1.0").build();
    }
}
