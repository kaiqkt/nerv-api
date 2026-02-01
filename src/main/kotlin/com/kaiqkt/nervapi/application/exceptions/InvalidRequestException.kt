package com.kaiqkt.nervapi.application.exceptions

class InvalidRequestException(
    val errors: Map<String, Any>,
) : Exception()
