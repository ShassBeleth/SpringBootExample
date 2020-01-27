package com.example.spring.boot.web.config

import com.example.spring.boot.web.annotation.IgnoreSwaggerDocument
import com.example.spring.boot.web.controller.spring.fox.example.ignore.IgnorePackageController
import com.fasterxml.classmate.TypeResolver
import com.google.common.base.Optional
import com.google.common.base.Predicates.not
import com.google.common.collect.Lists
import com.google.common.collect.Maps.newHashMap
import com.google.common.collect.Sets.newHashSet
import io.swagger.annotations.ApiModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.context.request.async.DeferredResult
import springfox.documentation.builders.*
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.schema.ModelRef
import springfox.documentation.schema.WildcardType
import springfox.documentation.service.*
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

    /**
     * コンテキストパス
     * application.propertiesからserver.servlet.context-pathを取得
     * 指定されていない場合は「/」を代入（pathMappingの引数にnullが入らない）
     */
    @Value("\${server.servlet.context-path:/}")
    private val contextPath : String? = null

    /**
     *　Getリクエストの共通レスポンス
     */
    private fun getGlobalGetResponses() : List<ResponseMessage> = Lists.newArrayList(
            ResponseMessageBuilder()
                    .code(500)
                    .message("Global 500 Error Message")
                    // TODO ModelRefではModelsに定義されているものなら参照可能か？
                    .responseModel(ModelRef("SampleResponse"))
                    // TODO ヘッダつけられないか？
                    .build(),
            ResponseMessageBuilder()
                    .code(400)
                    .message("Global 400 Error Message")
                    .responseModel(ModelRef("SampleResponse"))
                    .build()
    )
    /**
     * Deleteリクエストの共通レスポンス
     */
    private fun getGlobalDeleteResponses() : List<ResponseMessage> = ArrayList()
    /**
     * Headリクエストの共通レスポンス
     */
    private fun getGlobalHeadResponses() : List<ResponseMessage> = ArrayList()
    /**
     * Optionsリクエストの共通レスポンス
     */
    private fun getGlobalOptionsResponses() : List<ResponseMessage> = ArrayList()
    /**
     * Patchリクエストの共通レスポンス
     */
    private fun getGlobalPatchResponses() : List<ResponseMessage> = ArrayList()
    /**
     * Postリクエストの共通レスポンス
     */
    private fun getGlobalPostResponses() : List<ResponseMessage> = ArrayList()
    /**
     * Putリクエストの共通レスポンス
     */
    private fun getGlobalPutResponses() : List<ResponseMessage> = ArrayList()
    /**
     * Traceリクエストの共通レスポンス
     */
    private fun getGlobalTraceResponses() : List<ResponseMessage> = ArrayList()

    /**
     * 共通パラメータ
     */
    private fun getGlobalOperationParameters() : List<Parameter> = Lists.newArrayList(
            ParameterBuilder()
                    .name("globalParameter")
                    .description("これは全API共通のパラメータです")
                    .modelRef(ModelRef("string"))
                    .parameterType("query")
                    .required(true)
                    .build()
    )

    /**
     * APIの情報設定
     */
    private fun getApiInfo() : ApiInfo = ApiInfoBuilder()
            .title("Swagger Example Title")
            .description("Swagger Example Description")
            .contact(Contact("Contact name" , "Contact Url" , "Contact Email") )
            .license("License!" )
            .licenseUrl("License Url!")
            .termsOfServiceUrl("Terms Of Service Url!")
            .version("1.0.0")
            .build()

    @Bean
    fun configurateApiDocument(): Docket = Docket(DocumentationType.SWAGGER_2)

            // ApiSelectorBuilderでAPI仕様書の対象とするAPIを選ぶ
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
            // .paths(PathSelectors.any())
            // 全てのAPIを非表示
            // .paths(PathSelectors.none())
            // antPatternで該当のAPIを非表示
            // .paths(not(PathSelectors.ant("/sample/**")))
            // 特定のパスのAPIのみ非表示
            // ※「/error」はSpringが用意してくれてるデフォルトのエラー
            // 　API仕様書にはいらない
             .paths(not(regex("/error")))

            .build()

            // レスポンスのContent-Typeを指定する
            // ※ほとんどの場合が「application/json」だとは思うけど
            // 　個別に指定したい場合は定義しないほうがいいかも
            .produces(newHashSet("application/json"))

            // プロトコルを指定する
            .protocols(newHashSet("http","https"))

            // APIのパスにコンテキストパスを含める
            // ※pathMappingで指定しなくてもBaseUrlにはちゃんとコンテキストパスがついている
            // .pathMapping(contextPath)

            // Getリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.GET, getGlobalGetResponses() )
            // Deleteリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.DELETE , getGlobalDeleteResponses() )
            // Headリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.HEAD , getGlobalHeadResponses() )
            // Optionsリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.OPTIONS , getGlobalOptionsResponses() )
            // Patchリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.PATCH , getGlobalPatchResponses() )
            // Postリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.POST , getGlobalPostResponses() )
            // Putリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.PUT , getGlobalPutResponses() )
            // Traceリクエストの共通レスポンス
            .globalResponseMessage( RequestMethod.TRACE , getGlobalTraceResponses() )

            // デフォルトのレスポンスメッセージを使うかどうか
            // trueにすると特に明記がなくてもレスポンス401,403,404等が出力される
            // ※globalResponseMessageを使用するとtrueにしてもデフォルトのは消えるっぽい
            .useDefaultResponseMessages(false )

            // 全API共通パラメータ
            .globalOperationParameters( getGlobalOperationParameters() )

            // APIの情報設定
            .apiInfo( getApiInfo() )

            // TODO 以下あまり使い方よくわかってない
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
            .securitySchemes(Lists.newArrayList(ApiKey("mykey", "api_key", "header")))
            .securityContexts(Lists.newArrayList(SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex("/anyPath.*"))
                    .build())
            )
            .enableUrlTemplating(true)
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
