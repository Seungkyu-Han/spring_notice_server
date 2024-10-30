package org.seungkyu.notice.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PublishReq(
    @JsonProperty("data")
    val data: String
)
