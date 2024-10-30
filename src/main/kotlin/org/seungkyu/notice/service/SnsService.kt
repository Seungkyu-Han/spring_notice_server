package org.seungkyu.notice.service

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import lombok.SneakyThrows
import org.seungkyu.notice.config.AwsConfig
import org.seungkyu.notice.dto.PublishReq
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import software.amazon.awssdk.services.sns.model.PublishRequest

@Service
class SnsService(
    private val awsConfig: AwsConfig,
    private val objectMapper: ObjectMapper
) {
    @SneakyThrows
    suspend fun publish(serverRequest: ServerRequest): ServerResponse{
        val messageData = serverRequest.bodyToMono(PublishReq::class.java).awaitSingle()

        val message = objectMapper.writeValueAsString(messageData)

        val publishRequest = PublishRequest.builder()
            .topicArn(awsConfig.arn)
            .subject("AWS SNS 연습")
            .message(message)
            .build()

        val snsClient = awsConfig.snsClient()
        return ServerResponse.ok().bodyValueAndAwait(snsClient.publish(publishRequest).messageId())
    }
}