package com.example.spring.boot.web.spring.application.event.listener

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener

/**
 * アプリ起動中に発火するイベント
 * リフレッシュ後、アプリ起動完了時イベント
 */
class MyApplicationStartedEventListener : ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) = println("Application Started Event")
}
