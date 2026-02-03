package com.kaiqkt.nervapi.integration.web

import com.kaiqkt.nervapi.application.web.requests.ServerRequest
import com.kaiqkt.nervapi.application.web.responses.ErrorResponse
import com.kaiqkt.nervapi.application.web.responses.ServerResponse
import com.kaiqkt.nervapi.domain.exceptions.ErrorType
import com.kaiqkt.nervapi.domain.models.Project
import com.kaiqkt.nervapi.integration.IntegrationTest
import com.kaiqkt.nervapi.unit.resources.agent.helpers.AgentHelper
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ServerIntegrationTest : IntegrationTest() {
    @Test
    fun `given a server creation request should create successfully`() {
        val request =
            ServerRequest.CreateV1(
                name = "server-one",
                url = "http://localhost:8081/agent",
            )

        val project = projectRepository.save(Project(name = "project"))

        AgentHelper.mockHealthConnection()

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects/${project.id}/servers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .`as`(ServerResponse.V1::class.java)

        assertEquals(request.name, response.name)
        assertEquals(request.url, response.url)
    }

    @Test
    fun `given a server creation request when the request does not meet the requirements should return an error`() {
        val request =
            ServerRequest.CreateV1(
                name = "",
                url = "http:/localhost:8080",
            )

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects/1234/servers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .`as`(ErrorResponse::class.java)

        assertEquals("INVALID_REQUEST", response.type)
        assertEquals("Invalid request", response.message)
        assertEquals("must contain 1 to 25 characters", response.details["name"])
        assertEquals("URL must be a valid HTTP address", response.details["url"])
    }

    @Test
    fun `given a server creation request when the given agent url does not have a health connection should return an error`() {
        val request =
            ServerRequest.CreateV1(
                name = "server-one",
                url = "http://localhost:8080",
            )

        val project = projectRepository.save(Project(name = "project"))

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects/${project.id}/servers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .`as`(ErrorResponse::class.java)

        assertEquals(ErrorType.AGENT_CONNECTION_ERROR.name, response.type)
        assertEquals(ErrorType.AGENT_CONNECTION_ERROR.message, response.message)
    }

    @Test
    fun `given a server creation request when the given project does not exist should return an error`() {
        val request =
            ServerRequest.CreateV1(
                name = "server-one",
                url = "http://localhost:8080",
            )

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects/1234/servers")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .`as`(ErrorResponse::class.java)

        assertEquals(ErrorType.PROJECT_NOT_FOUND.name, response.type)
        assertEquals(ErrorType.PROJECT_NOT_FOUND.message, response.message)
    }
}
