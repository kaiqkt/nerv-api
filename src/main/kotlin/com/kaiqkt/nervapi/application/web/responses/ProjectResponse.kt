package com.kaiqkt.nervapi.application.web.responses

import com.kaiqkt.nervapi.domain.models.Project
import java.time.LocalDateTime

sealed class ProjectResponse {
    data class V1(
        val id: String,
        val name: String,
        val description: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime?,
    ) : ProjectResponse()
}

fun Project.toV1(): ProjectResponse.V1 =
    ProjectResponse.V1(
        id = this.id,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
