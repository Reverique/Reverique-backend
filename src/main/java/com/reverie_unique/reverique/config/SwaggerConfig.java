package com.reverie_unique.reverique.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Reverique backend API")
                        .description("Reverique 애플리케이션의 백엔드 API로, 사용자 관리 및 데이터 처리 기능을 제공합니다.")
                        .version("1.0.0"));
    }
}