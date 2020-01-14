package com.example.spring.boot.web.failure.analyzer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class TempComponent {
    fun temp() {
        throw TempException("Temp!")
    }
}

@Component
class TempAppListener : ApplicationListener<ContextRefreshedEvent?> {
    @Autowired
    private val tempService: TempComponent? = null
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        tempService!!.temp()
    }
}

class TempException(message: String?) : RuntimeException(message)

class TempFailureAnalyzer : AbstractFailureAnalyzer<TempException>() {
    override fun analyze(rootFailure: Throwable, cause: TempException): FailureAnalysis {
        return FailureAnalysis(
                cause.message,
                "Temp!!!",
                cause
        )
    }
}
