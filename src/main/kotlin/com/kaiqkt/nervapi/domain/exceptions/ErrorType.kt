package com.kaiqkt.nervapi.domain.exceptions

enum class ErrorType(
    val message: String,
) {
    DEFAULT("DEFAULT"),
    PROJECT_NAME_CONFLICT("Project already exists with the given name"),
    PROJECT_NOT_FOUND("Project not found"),
    AGENT_CONNECTION_ERROR("Error connecting with the agent"),
}
