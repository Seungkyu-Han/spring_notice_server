package org.seungkyu.notice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

@Configuration
class AwsConfig(
    @Value("\${spring.cloud.aws.credentials.access-key}")
    private val accessKey: String,
    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private val secretKey: String,
    @Value("\${spring.cloud.aws.region.static}")
    private val region: String,
    @Value("\${spring.cloud.aws.sns.topic.arn}")
    val arn: String
) {

    @Bean
    fun snsClient(): SnsClient =
        SnsClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build()
}