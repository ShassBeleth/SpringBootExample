package com.example.spring.boot.web.failure.analyzer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * 独自の例外
 */
class SampleException(message: String?) : RuntimeException(message)

/**
 * カスタムFailureAnalyzerのサンプル
 * アプリ起動中に発生したSampleExceptionをつかむ
 */
class SampleFailureAnalyzer : AbstractFailureAnalyzer<SampleException>() {
    override fun analyze(rootFailure: Throwable, cause: SampleException): FailureAnalysis {
        return FailureAnalysis(
                cause.message,
                "Sample Exception!",
                cause
        )
    }
}

// 以下SampleExceptionをアプリ起動中に発生させるサンプル
//
///**
// * アプリ起動中に読み込まれるサンプルComponent
// */
//@Component
//class SampleComponent {
//    fun run() {
//        throw SampleException("exception!")
//    }
//}
//
///**
// * リフレッシュ時にイベント発火するListenerクラス
// */
//@Component
//class SampleApplicationListener : ApplicationListener<ContextRefreshedEvent?> {
//
//    @Autowired
//    private val sampleComponent: SampleComponent? = null
//
//    /**
//     * リフレッシュ時にSampleComponentのrunを実行する(例外を発生させる)
//     */
//    override fun onApplicationEvent(event: ContextRefreshedEvent) {
//        sampleComponent!!.run()
//    }
//
//}
