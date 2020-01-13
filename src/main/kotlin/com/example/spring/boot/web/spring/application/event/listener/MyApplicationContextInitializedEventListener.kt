package com.example.spring.boot.web.spring.application.event.listener

import org.springframework.boot.context.event.ApplicationContextInitializedEvent
import org.springframework.context.ApplicationListener

/**
 * ApplicationContextInitializersコール時イベント(Beanロード前)
 */
class MyApplicationContextInitializedEventListener : ApplicationListener<ApplicationContextInitializedEvent> {
    override fun onApplicationEvent(event: ApplicationContextInitializedEvent) = println("Application Context Initialized Event")
}
