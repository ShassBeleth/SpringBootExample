package com.example.spring.boot.web.spring.application.event.listener

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener

/**
 * Environment作成前イベント
 */
class MyApplicationEnvironmentPreparedEventListener : ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) = println("Application Environment Prepared Event")
}
