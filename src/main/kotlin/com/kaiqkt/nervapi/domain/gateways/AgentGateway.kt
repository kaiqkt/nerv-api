package com.kaiqkt.nervapi.domain.gateways

interface AgentGateway {
    fun isHealth(url: String): Boolean
}
