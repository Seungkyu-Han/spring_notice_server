package org.seungkyu.notice.service

import lombok.SneakyThrows
import org.seungkyu.notice.config.AwsConfig
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.PublishRequest
import software.amazon.awssdk.services.sns.model.SubscribeRequest

@Service
class SnsService(
    private val awsConfig: AwsConfig
) {

    private val snsClient = awsConfig.snsClient()

    companion object{
        private val log = LoggerFactory.getLogger(SnsService::class.java)
    }
    @SneakyThrows
    suspend fun publish(serverRequest: ServerRequest): ServerResponse{

        val createTopicRequest = CreateTopicRequest.builder()
            .name("study")
            .build()

        val createTopicResponse = snsClient.createTopic(createTopicRequest)

        log.info("topic name = {}", createTopicResponse.topicArn())
        log.info("topic list = {}", snsClient.listTopics())

        return ServerResponse.ok().bodyValueAndAwait(createTopicResponse.topicArn())
    }

    suspend fun subscribe(serverRequest: ServerRequest): ServerResponse{
        val endPoint = serverRequest.queryParamOrNull("endpoint") ?:
        return ServerResponse.badRequest().buildAndAwait()
        val topicArn = serverRequest.queryParamOrNull("topicArn") ?:
        return ServerResponse.badRequest().buildAndAwait()

        val subscribeRequest = SubscribeRequest.builder()
            .protocol("https")
            .topicArn(topicArn)
            .endpoint(endPoint)
            .build()

        log.info("topicArn to subscribe: {}", snsClient.subscribe(subscribeRequest).subscriptionArn())

        log.info("subscription list = {}", snsClient.listSubscriptions())

        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun postMessage(serverRequest: ServerRequest): ServerResponse {
        val publishRequest = PublishRequest.builder()
            .topicArn(awsConfig.arn)
            .subject("TEST")
            .message(mapOf("hello" to "hello!!").toString()).build()

        snsClient.publish(publishRequest)

        return ServerResponse.ok().buildAndAwait()
    }
}