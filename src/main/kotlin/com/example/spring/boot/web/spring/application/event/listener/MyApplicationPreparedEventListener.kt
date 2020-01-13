package com.example.spring.boot.web.spring.application.event.listener

import org.springframework.boot.context.event.ApplicationPreparedEvent
import org.springframework.context.ApplicationListener

/**
 * Beanロード後、リフレッシュ前イベント
 */
class MyApplicationPreparedEventListener : ApplicationListener<ApplicationPreparedEvent> {
    override fun onApplicationEvent(event: ApplicationPreparedEvent) = println("Application Prepared Event")
}
