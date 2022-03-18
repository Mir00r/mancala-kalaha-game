package com.bol.mancala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        List<ResponseMessage> responseMessageList = new java.util.ArrayList<>();

        responseMessageList.add(new ResponseMessageBuilder().code(200).message("Successfully retrieved list")
                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(201).message("Successfully created the requested resource")
                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("You are not authorized to view the resource")
                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("Accessing the resource you were trying to reach is forbidden")
                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found")
                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(406).message("The resource you were trying to reach is not acceptable")
                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("Internal server error")
                .responseModel(new ModelRef("string")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList);
    }
}
