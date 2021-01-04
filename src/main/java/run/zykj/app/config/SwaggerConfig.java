package run.zykj.app.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lingSong
 * @date 2020/12/30 21:21
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enabled}")
    private boolean enabled;

    @Value("${swagger.pathMapping}")
    private String pathMapping;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //可设置扫描那些包
                .paths(PathSelectors.any())
                .build()
                .pathMapping(pathMapping);
    }

    /**
     * 添加摘要信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题: 接口文档")
                // 描述
                .description("描述： 用于查看后端功能性")
                // 版本
                .version("版本号: 0.0.1")
                .build();
    }

}
