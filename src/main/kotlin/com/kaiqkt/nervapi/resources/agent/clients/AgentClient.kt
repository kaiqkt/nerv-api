package com.kaiqkt.nervapi.resources.agent.clients

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.kaiqkt.nervapi.domain.utils.Constants
import com.kaiqkt.nervapi.domain.utils.MetricsUtils
import org.springframework.stereotype.Component

@Component
class AgentClient(
    private val mapper: ObjectMapper,
    private val metricsUtils: MetricsUtils,
) {
    fun healthcheck(url: String): Boolean {
        val (_, response, _) =
            metricsUtils.timer(AGENT_METRIC) {
                Fuel
                    .get("$url/health")
                    .header(
                        mapOf(
                            "Content-Type" to "application/json",
                            "X-Request-Id" to Constants.Parameters.requestId,
                        ),
                    ).response()
            }

        return response.isSuccessful
    }

    companion object {
        private const val AGENT_METRIC = "agent"
    }
}
