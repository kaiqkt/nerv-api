package com.kaiqkt.nervapi.application.web.requests

import com.kaiqkt.nervapi.domain.models.Project
import jakarta.validation.constraints.Size

sealed class ProjectRequest {
    data class CreateV1(
        @field:Size(message = "must contain 1 to 25 characters", min = 1, max = 25)
        val name: String,
        @field:Size(message = "must not exceed 255 characters", max = 255)
        val description: String?,
    ) : ProjectRequest()
}

fun ProjectRequest.CreateV1.toDomain(): Project =
    Project(
        name = this.name,
        description = this.description,
    )
