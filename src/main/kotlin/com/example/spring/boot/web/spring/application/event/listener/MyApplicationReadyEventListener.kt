package com.example.spring.boot.web.spring.application.event.listener

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

/**
 * アプリケーションがリクエストを受け付けられるようになった時のイベント
 */
class MyApplicationReadyEventListener : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) = println("Application Ready Event")
}
