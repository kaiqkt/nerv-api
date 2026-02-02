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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/projects")
class ProjectController(
    private val projectService: ProjectService,
) {
    @PostMapping
    fun create(
        @Valid @RequestBody request: ProjectRequest.CreateV1,
    ): ResponseEntity<ProjectResponse.V1> {
        val project = projectService.create(request.toDomain())

        return ResponseEntity.ok(project.toV1())
    }
}
