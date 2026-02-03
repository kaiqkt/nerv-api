package com.kaiqkt.nervapi.application.web.controllers

import com.kaiqkt.nervapi.application.web.requests.ServerRequest
import com.kaiqkt.nervapi.application.web.requests.toDomain
import com.kaiqkt.nervapi.application.web.responses.ServerResponse
import com.kaiqkt.nervapi.application.web.responses.toV1
import com.kaiqkt.nervapi.domain.services.ServerService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ServerController(
    private val serverService: ServerService,
) {
    @PostMapping("/v1/projects/{project_id}/servers")
    fun create(
        @PathVariable("project_id") projectId: String,
        @Valid @RequestBody request: ServerRequest.CreateV1,
    ): ResponseEntity<ServerResponse.V1> {
        val server = serverService.create(projectId, request.toDomain())

        return ResponseEntity.ok(server.toV1())
    }
}
