package com.example.spring.boot.web

import com.example.spring.boot.web.spring.application.event.listener.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.system.exitProcess


/**
 * エントリポイント
 */

@SpringBootApplication

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