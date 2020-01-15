package com.example.spring.boot.web.failure.analyzer

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis
import org.springframework.boot.web.server.PortInUseException

/**
 * アプリ起動時に指定のポートが既に使われていた場合に起きるExceptionの分析
 */
class MyPortInUseFailureAnalyzer : AbstractFailureAnalyzer<PortInUseException>() {
	override fun analyze(rootFailure:Throwable, cause:PortInUseException):FailureAnalysis {
        return FailureAnalysis(
                "Web server failed to start. Port " + cause.port + " was already in use." ,
                "Identify and stop the process that's listening on port " + cause.port + " or configure this application to listen on another port.",
                cause
        )
    }
}