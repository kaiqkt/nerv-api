package com.kaiqkt.nervapi.unit.resources.agent.helpers

import com.kaiqkt.nervapi.unit.resources.MockServerHolder
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

object AgentHelper : MockServerHolder() {
    override fun domainPath(): String = "/agent"

    fun mockHealthConnection() {
        mockServer()
            .`when`(
                HttpRequest
                    .request()
                    .withMethod(HttpMethod.GET.name())
                    .withPath("${domainPath()}/health"),
            ).respond(
                HttpResponse
                    .response()
                    .withStatusCode(HttpStatus.OK.value()),
            )
    }
}
