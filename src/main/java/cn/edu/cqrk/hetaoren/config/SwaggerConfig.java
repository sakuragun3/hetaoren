package cn.edu.cqrk.hetaoren.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bill
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 根据basePackage指定的包进行扫描,把扫描到的接口自动生成文档
                .apis(RequestHandlerSelectors.basePackage("cn.edu.cqrk.hetaoren.controller"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Timestamp.class)
                .securitySchemes(security())
                .securityContexts(securityContexts());
    }
    // 指定每次访问携带token
    private List<ApiKey> security(){
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("token","token","header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(new SecurityReference("token", scopes()));
        securityContextList.add(SecurityContext
                .builder()
                .securityReferences(securityReferenceList)
                .forPaths(PathSelectors.any())
                .build()
        );
        return securityContextList;
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};
    }



    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("核桃仁癫痫病医疗管理系统")
                .description("如果没能一次成功，那就叫它1.0版吧")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
