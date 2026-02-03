package com.kaiqkt.nervapi.application.web.requests

import com.kaiqkt.nervapi.domain.dtos.ServerDto
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

sealed class ServerRequest {
    data class CreateV1(
        @field:Size(message = "must contain 1 to 25 characters", min = 1, max = 25)
        val name: String,
        @field:Pattern(
            regexp = "^http://((localhost)|(([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,})|(\\d{1,3}(\\.\\d{1,3}){3}))(:\\d{1,5})?(/\\S*)?$",
            message = "URL must be a valid HTTP address",
        )
        val url: String,
    )
}

fun ServerRequest.CreateV1.toDomain(): ServerDto =
    ServerDto(
        name = this.name,
        url = this.url,
    )
