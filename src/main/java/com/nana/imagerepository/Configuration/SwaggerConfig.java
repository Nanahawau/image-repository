package com.nana.imagerepository.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     *
     * @return Docket
     */
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("image-repository")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    /**
     *
     * @return
     * Predicates return a true or false value for a given input
     */
    private Predicate<String> postPaths() {
        return or(regex("/api/v1.*"), regex("/api.*"));
    }

    /**
     *
     * @return ApiInfo Class
     * It's a class that allows you describe your APIs using additional information
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Image Repository")
                .description("This project is an image repository that takes advantage of\n" +
                        "the minio's object storage capabilities to store images one at a time and \n" +
                        "in bulk.")
                .termsOfServiceUrl("")
                .contact("nanaadeku@gmail.com").license("M.I.T")
                .licenseUrl("nanaadeku@gmail.com").version("1.0").build();
    }
}
