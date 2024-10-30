package org.seungkyu.notice.router

import org.seungkyu.notice.service.SnsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class SNSRouter {

    @Bean
    fun snsRouters(
        snsService: SnsService
    ) = coRouter{
        "/sns".nest {
            POST("", snsService::publish)
            GET("", snsService::subscribe)
            POST("/message", snsService::postMessage)
        }
    }
}