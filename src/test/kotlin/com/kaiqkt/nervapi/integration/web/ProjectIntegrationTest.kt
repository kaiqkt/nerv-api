package com.kaiqkt.nervapi.integration.web

import com.kaiqkt.nervapi.application.web.requests.ProjectRequest
import com.kaiqkt.nervapi.application.web.responses.ErrorResponse
import com.kaiqkt.nervapi.application.web.responses.ProjectResponse
import com.kaiqkt.nervapi.domain.exceptions.ErrorType
import com.kaiqkt.nervapi.domain.models.Project
import com.kaiqkt.nervapi.integration.IntegrationTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ProjectIntegrationTest : IntegrationTest() {
    @Test
    fun `given a project creation request when not exists a project with the given name should create successfully`() {
        val request =
            ProjectRequest.CreateV1(
                name = "first-project",
                description = "minimal description",
            )

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .`as`(ProjectResponse.V1::class.java)

        assertEquals(request.name, response.name)
        assertEquals(request.description, response.description)
    }

    @Test
    fun `given a project creation request when exists a project with the given name should return an error`() {
        val request =
            ProjectRequest.CreateV1(
                name = "first-project",
                description = "minimal description",
            )

        val project = Project(name = "first-project")
        projectRepository.save(project)

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .extract()
                .`as`(ErrorResponse::class.java)

        assertEquals(ErrorType.PROJECT_NAME_CONFLICT.name, response.type)
        assertEquals(ErrorType.PROJECT_NAME_CONFLICT.message, response.message)
    }

    @Test
    fun `given a project creation request when the request does not meet the minimum requirements should return an error`() {
        val request =
            ProjectRequest.CreateV1(
                name = "",
                description = "minimal description".repeat(256),
            )

        val response =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/v1/projects")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .`as`(ErrorResponse::class.java)

        assertEquals("INVALID_REQUEST", response.type)
        assertEquals("Invalid request", response.message)
        assertEquals("must contain 1 to 25 characters", response.details["name"])
        assertEquals("must not exceed 255 characters", response.details["description"])
    }
}
