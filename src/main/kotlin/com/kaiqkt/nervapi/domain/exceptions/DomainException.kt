package com.kaiqkt.nervapi.domain.exceptions

class DomainException(
    val type: ErrorType,
) : Exception(type.message)
