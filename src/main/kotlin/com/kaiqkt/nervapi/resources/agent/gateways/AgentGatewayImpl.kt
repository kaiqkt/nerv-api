package com.kaiqkt.nervapi.resources.agent.gateways

import com.kaiqkt.nervapi.domain.gateways.AgentGateway
import com.kaiqkt.nervapi.resources.agent.clients.AgentClient
import org.springframework.stereotype.Component

@Component
class AgentGatewayImpl(
    private val agentClient: AgentClient,
) : AgentGateway {
    override fun isHealth(url: String): Boolean = agentClient.healthcheck(url)

    companion object {
        private const val STATUS_UP = "UP"
    }
}
