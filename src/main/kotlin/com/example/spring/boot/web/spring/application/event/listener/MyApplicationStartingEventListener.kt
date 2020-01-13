package com.example.spring.boot.web.spring.application.event.listener

import org.springframework.boot.context.event.ApplicationStartingEvent
import org.springframework.context.ApplicationListener

/**
 * アプリ開始時イベント
 */
class MyApplicationStartingEventListener : ApplicationListener<ApplicationStartingEvent> {
    override fun onApplicationEvent(event: ApplicationStartingEvent) = println("Application Starting Event")
}