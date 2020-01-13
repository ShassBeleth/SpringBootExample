package com.example.spring.boot.web

import com.example.spring.boot.web.spring.application.event.listener.*
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.*
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.ComponentScan

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

	// 各種イベント追加
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

