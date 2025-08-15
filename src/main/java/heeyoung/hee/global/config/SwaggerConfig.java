package heeyoung.hee.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        Components components = new Components()
                .addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .name("BearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        Server prodServer = new Server();
        prodServer.setUrl("https://heeyoung.inuappcenter.kr");
        prodServer.setDescription("Production Server");

        Server devServer = new Server();
        prodServer.setUrl("http://localhost:8080");
        devServer.setDescription("Dev Server");

        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(prodServer,devServer));
    }

    private Info apiInfo() {
        return new Info()
                .title("Appcenter Basic Summer Project API Docs")
                .version("1.0.0")
                .description("앱센터 17기 베이직 하계 프로젝트 API Docs");
    }
}
