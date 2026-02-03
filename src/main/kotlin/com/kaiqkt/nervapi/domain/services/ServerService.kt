package com.kaiqkt.nervapi.domain.services

import com.kaiqkt.nervapi.domain.dtos.ServerDto
import com.kaiqkt.nervapi.domain.exceptions.DomainException
import com.kaiqkt.nervapi.domain.exceptions.ErrorType
import com.kaiqkt.nervapi.domain.gateways.AgentGateway
import com.kaiqkt.nervapi.domain.models.Server
import com.kaiqkt.nervapi.domain.repositories.ServerRepository
import com.kaiqkt.nervapi.domain.utils.Constants
import com.kaiqkt.nervapi.domain.utils.MetricsUtils
import org.springframework.stereotype.Service

@Service
class ServerService(
    private val serverRepository: ServerRepository,
    private val projectService: ProjectService,
    private val agentGateway: AgentGateway,
    private val metricsUtils: MetricsUtils,
) {
    fun create(
        projectId: String,
        serverDto: ServerDto,
    ): Server {
        val project = projectService.findById(projectId)

        if (!agentGateway.isHealth(serverDto.url)) {
            metricsUtils.counter(
                SERVER_METRIC,
                Constants.Metrics.ACTION,
                Constants.Metrics.CREATE,
                Constants.Metrics.STATUS,
                Constants.Metrics.ERROR,
            )
            throw DomainException(ErrorType.AGENT_CONNECTION_ERROR)
        }

        val server =
            Server(
                name = serverDto.name,
                url = serverDto.url,
                project = project,
            )

        return serverRepository.save(server).also {
            metricsUtils.counter(
                SERVER_METRIC,
                Constants.Metrics.ACTION,
                Constants.Metrics.CREATE,
                Constants.Metrics.STATUS,
                Constants.Metrics.SUCCESS,
            )
        }
    }

    companion object {
        private const val SERVER_METRIC = "server"
    }
}
