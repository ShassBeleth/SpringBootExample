package com.example.spring.boot.web.config

import com.example.spring.boot.web.annotation.IgnoreSwaggerDocument
import com.example.spring.boot.web.controller.spring.fox.example.ignore.IgnorePackageController
import com.fasterxml.classmate.TypeResolver
import com.google.common.base.Predicates.not
import com.google.common.collect.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.context.request.async.DeferredResult
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.schema.ModelRef
import springfox.documentation.schema.WildcardType
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.*
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate

/**
 * Swaggerの設定
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Autowired
    private val typeResolver: TypeResolver? = null

    @Bean
    fun configurateApiDocument(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()

            // API仕様書に表示するAPIをアノテーションで指定
            // 全てのAPIを表示
            // .apis(RequestHandlerSelectors.any())
            // 全てのAPIを非表示
            // .apis(RequestHandlerSelectors.none())
            // 特定のアノテーションがクラスについているAPIのみ非表示
            .apis(not(RequestHandlerSelectors.withClassAnnotation(IgnoreSwaggerDocument::class.java)))
            // 特定のアノテーションがメソッドについているAPIのみ非表示
            .apis(not(RequestHandlerSelectors.withMethodAnnotation(IgnoreSwaggerDocument::class.java)))
            // 特定のパッケージのAPIのみ非表示
            .apis(not(RequestHandlerSelectors.basePackage(IgnorePackageController::class.java.packageName)))

            // 全てのAPIを表示
             .paths(PathSelectors.any())
            // 全てのAPIを非表示
            // .paths(PathSelectors.none())
            // antPatternで該当のAPIを非表示
            // .paths(not(PathSelectors.ant("/sample/**")))
            // 特定のパスのAPIのみ非表示
            // .paths(not(regex("/ignore/*")))

             .build()
            .pathMapping("/")
            .directModelSubstitute(LocalDate::class.java, String::class.java)
            .genericModelSubstitutes(ResponseEntity::class.java)
            .alternateTypeRules(
                    AlternateTypeRules.newRule(
                            typeResolver!!.resolve(
                                    DeferredResult::class.java,
                                    typeResolver!!.resolve(ResponseEntity::class.java, WildcardType::class.java)
                            ),
                            typeResolver!!.resolve(WildcardType::class.java)
                    )
            )
            .useDefaultResponseMessages(false)
            .globalResponseMessage(
                    RequestMethod.GET,
                    Lists.newArrayList(
                            ResponseMessageBuilder()
                                    .code(500)
                                    .message("500 message")
                                    .responseModel(ModelRef("Error"))
                                    .build()
                    )
            )
            .securitySchemes(Lists.newArrayList(ApiKey("mykey", "api_key", "header")))
            .securityContexts(Lists.newArrayList(SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex("/anyPath.*"))
                    .build())
            )
            .enableUrlTemplating(true)
            .globalOperationParameters(
                    Lists.newArrayList(ParameterBuilder()
                            .name("someGlobalParameter")
                            .description("Description of someGlobalParameter")
                            .modelRef(ModelRef("string"))
                            .parameterType("query")
                            .required(true)
                            .build()
                    )
            )
            .tags(Tag("Pet Service", "All apis relating to pets"))

            // .additionalModels(typeResolver!!.resolve(AdditionalModel::class.java))

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = AuthorizationScope("global", "accessEverything")
        return Lists.newArrayList(SecurityReference("mykey", authorizationScopes))
    }

    @Bean
    fun security(): SecurityConfiguration = SecurityConfigurationBuilder
            .builder()
            .clientId("test-app-client-id")
            .clientSecret("test-app-client-secret")
            .realm("test-app-realm")
            .appName("test-app")
            .scopeSeparator(",")
            .additionalQueryStringParams(null)
            .useBasicAuthenticationWithAccessCodeGrant(false)
            .build()

    @Bean
    fun uiConfig(): UiConfiguration = UiConfigurationBuilder
            .builder()
            .deepLinking(true)
            .displayOperationId(false)
            .defaultModelsExpandDepth(1)
            .defaultModelExpandDepth(1)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
            .validatorUrl(null)
            .build()

}