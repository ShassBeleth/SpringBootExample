package com.example.spring.boot.web.failure.analyzer

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis
import org.springframework.boot.web.server.PortInUseException

class MyPortInUseExceptionFailureAnalyzer : AbstractFailureAnalyzer<PortInUseException>() {
    override fun analyze(rootFailure: Throwable, cause: PortInUseException): FailureAnalysis {
        return FailureAnalysis(
                cause.message,
                "Port already in use!!!",
                cause
        )
    }
}