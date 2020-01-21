package com.example.spring.boot.web

import com.example.spring.boot.web.sample.HogeController
import com.example.spring.boot.web.sample.SampleController
import com.example.spring.boot.web.sample.SampleResponse
import com.example.spring.boot.web.spring.application.event.listener.*
import com.fasterxml.classmate.TypeResolver
import com.google.common.collect.Lists.newArrayList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import springfox.documentation.swagger2.annotations.EnableSwagger2
import kotlin.system.exitProcess
import springfox.documentation.service.SecurityReference
import springfox.documentation.builders.PathSelectors
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.service.ApiKey
import springfox.documentation.schema.ModelRef
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.ResponseMessageBuilder
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult
import java.time.LocalDate
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.AlternateTypeRules.newRule
import springfox.documentation.schema.WildcardType
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.*


/**
 * エントリポイント
 */

@SpringBootApplication
@EnableSwagger2
@ComponentScan( basePackageClasses = [ SampleController::class , HogeController::class ] )

// @SpringBootApplicationを使用せずに以下の2つのアノテーションでエントリポイントを指定できる
// @EnableAutoConfiguration
// @ComponentScan

class SpringBootExampleApplication

///**
// *　4.1.10 Application Exit
// * TODO Qiitaまとめてない
// */
//@Bean
//fun exitCodeGenerator(): ExitCodeGenerator? {
//	return ExitCodeGenerator { 42 }
//}

/**
 * エントリポイント
 */
fun main(args: Array<String>) {
	println("start main method")

	val application = SpringApplication(SpringBootExampleApplication::class.java)

	// アプリ起動中に発火する各種イベント追加
	application.addListeners( MyApplicationStartingEventListener() )
	application.addListeners( MyApplicationEnvironmentPreparedEventListener() )
	application.addListeners( MyApplicationContextInitializedEventListener() )
	application.addListeners( MyApplicationPreparedEventListener() )
	application.addListeners( MyApplicationStartedEventListener() )
	application.addListeners( MyApplicationReadyEventListener() )

	application.run(*args)

	// Spring Initializrでの生成時に記載されてたアプリ起動メソッド
	// これの代わりにapplication.run(*args)を使用している
	// runApplication<SpringBootExampleApplication>(*args)

}

///**
// * 4.1.8 Accessing Application Arguments
// * TODO Qiitaまとめてない
// */
//@Component
//class ApplicationArgumentsSample @Autowired constructor(args: ApplicationArguments) {
//	init {
//		println( "Application Arguments Sample Constructor" )
//		val debug = args.containsOption("debug") // 例 > java -jar hoge.jar --debug
//		val nonOptionArgs = args.nonOptionArgs // 例 > java -jar hoge.jar aaa bbb ccc
//		println( debug )
//		for( nonOptionArg in nonOptionArgs ) {
//			println( nonOptionArg )
//		}
//	}
//}
///**
// * 4.1.9 Command Line Runner
// * TODO Qiitaまとめてない
// */
//@Order( 2 )
//@Component
//class MyCommandLineRunner1 : CommandLineRunner {
//	override fun run(vararg args: String) {
//		println( "Command Line Runner 1" )
//	}
//}
//@Order( 1 )
//@Component
//class MyCommandLineRunner2 : CommandLineRunner {
//	override fun run(vararg args: String) {
//		println( "Command Line Runner 2" )
//	}
//}


@Autowired
private val typeResolver: TypeResolver? = null

@Bean
fun petApi(): Docket = Docket(DocumentationType.SWAGGER_2)
    .select()
    .apis(RequestHandlerSelectors.any())
    .paths(PathSelectors.any())
    .build()
    .pathMapping("/")
    .directModelSubstitute(LocalDate::class.java, String::class.java)
    .genericModelSubstitutes(ResponseEntity::class.java)
    .alternateTypeRules(
            newRule(
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
            newArrayList(
                    ResponseMessageBuilder()
                            .code(500)
                            .message("500 message")
                            .responseModel(ModelRef("Error"))
                            .build()
            )
    )
    .securitySchemes(newArrayList(ApiKey("mykey", "api_key", "header")))
    .securityContexts(newArrayList(SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/anyPath.*"))
            .build())
    )
    .enableUrlTemplating(true)
    .globalOperationParameters(
            newArrayList(ParameterBuilder()
                    .name("someGlobalParameter")
                    .description("Description of someGlobalParameter")
                    .modelRef(ModelRef("string"))
                    .parameterType("query")
                    .required(true)
                    .build()
            )
    )
    .tags(Tag("Pet Service", "All apis relating to pets"))
//    .additionalModels(typeResolver!!.resolve(AdditionalModel::class.java))

fun defaultAuth(): List<SecurityReference> {
    val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
    authorizationScopes[0] = AuthorizationScope("global", "accessEverything")
    return newArrayList(SecurityReference("mykey", authorizationScopes))
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
