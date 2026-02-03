package com.kaiqkt.nervapi.domain.utils

import org.slf4j.MDC
import java.util.UUID

object Constants {
    object Parameters {
        val requestId = MDC.get("request_id") ?: UUID.randomUUID().toString()
    }

    object Metrics {
        const val STATUS = "status"
        const val ACTION = "action"
        const val CREATE = "create"
        const val CONFLICT = "conflict"
        const val ERROR = "error"
        const val SUCCESS = "success"
    }

    object Keys

    object Headers

    object Sort
}
