package com.example.spring.boot.web

import com.example.spring.boot.web.spring.application.event.listener.*
import org.springframework.beans.factory.BeanNotOfRequiredTypeException
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis


/**
 * エントリポイント
 */

@SpringBootApplication

// @SpringBootApplicationを使用せずに以下の2つのアノテーションでエントリポイントを指定できる
// @EnableAutoConfiguration
// @ComponentScan

class SpringBootExampleApplication

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
