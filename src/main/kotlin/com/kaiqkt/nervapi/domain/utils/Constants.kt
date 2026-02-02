package com.kaiqkt.nervapi.domain.utils

import org.slf4j.MDC
import java.util.UUID

object Constants {
    object Parameters {
        val requestId = MDC.get("request_id") ?: UUID.randomUUID().toString()
    }

    object Metrics {
        const val STATUS = "status"
        const val CONFLICT = "conflict"
        const val CREATED = "created"
    }

    object Keys

    object Headers

    object Sort
}
