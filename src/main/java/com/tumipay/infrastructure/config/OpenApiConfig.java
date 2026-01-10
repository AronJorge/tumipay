package com.tumipay.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tumipay.infrastructure.adapter.input.rest.constant.RestConstants;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tumiPayOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(RestConstants.Docs.TITLE)
                        .description(RestConstants.Docs.DESCRIPTION)
                        .version(RestConstants.Docs.VERSION));
    }
}
