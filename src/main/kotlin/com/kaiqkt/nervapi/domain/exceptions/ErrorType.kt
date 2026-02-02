package com.kaiqkt.nervapi.domain.exceptions

enum class ErrorType(
    val message: String,
) {
    DEFAULT("DEFAULT"),
    PROJECT_NAME_CONFLICT("Project already exists with the given name"),
}
