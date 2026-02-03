package com.kaiqkt.nervapi.application.web.responses

import com.kaiqkt.nervapi.domain.models.Server
import java.time.LocalDateTime

sealed class ServerResponse {
    data class V1(
        val id: String,
        val name: String,
        val url: String,
        val createdAt: LocalDateTime,
    ) : ServerResponse()
}

fun Server.toV1(): ServerResponse.V1 =
    ServerResponse.V1(
        id = this.id,
        name = this.name,
        url = this.url,
        createdAt = this.createdAt,
    )
