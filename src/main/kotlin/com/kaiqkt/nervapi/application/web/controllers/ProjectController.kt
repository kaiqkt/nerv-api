package com.kaiqkt.nervapi.application.web.controllers

import com.kaiqkt.nervapi.application.web.requests.ProjectRequest
import com.kaiqkt.nervapi.application.web.requests.toDomain
import com.kaiqkt.nervapi.application.web.responses.ProjectResponse
import com.kaiqkt.nervapi.application.web.responses.toV1
import com.kaiqkt.nervapi.domain.services.ProjectService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    private val projectService: ProjectService,
) {
    @PostMapping("/v1/projects")
    fun create(
        @Valid @RequestBody request: ProjectRequest.CreateV1,
    ): ResponseEntity<ProjectResponse.V1> {
        val project = projectService.create(request.toDomain())

        return ResponseEntity.ok(project.toV1())
    }
}
