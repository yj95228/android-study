package com.scsa.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class MemoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoApiApplication.class, args);
	}
	
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("scsaMemo")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.scsa.memo.controller"))
				.paths(PathSelectors.ant("/api/**"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SCSA API")
				.description("SCSA MEMO API Reference for Developers")
				.termsOfServiceUrl("https://www.scsa.com")
				.license("SCSA License")
				.licenseUrl("scsa@scsa.com").version("1.0").build();
	}


}
