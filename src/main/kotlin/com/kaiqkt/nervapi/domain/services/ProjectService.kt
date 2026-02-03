package com.kaiqkt.nervapi.domain.services

import com.kaiqkt.nervapi.domain.exceptions.DomainException
import com.kaiqkt.nervapi.domain.exceptions.ErrorType
import com.kaiqkt.nervapi.domain.models.Project
import com.kaiqkt.nervapi.domain.repositories.ProjectRepository
import com.kaiqkt.nervapi.domain.utils.Constants
import com.kaiqkt.nervapi.domain.utils.MetricsUtils
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val metricsUtils: MetricsUtils,
) {
    fun create(project: Project): Project {
        if (projectRepository.existsByName(project.name)) {
            metricsUtils.counter(
                PROJECT_METRIC,
                Constants.Metrics.ACTION,
                Constants.Metrics.CREATE,
                Constants.Metrics.STATUS,
                Constants.Metrics.CONFLICT,
            )
            throw DomainException(ErrorType.PROJECT_NAME_CONFLICT)
        }

        return projectRepository.save(project).also {
            metricsUtils.counter(
                PROJECT_METRIC,
                Constants.Metrics.ACTION,
                Constants.Metrics.CREATE,
                Constants.Metrics.STATUS,
                Constants.Metrics.SUCCESS,
            )
        }
    }

    fun findById(projectId: String): Project =
        projectRepository.findById(projectId).getOrNull() ?: throw DomainException(ErrorType.PROJECT_NOT_FOUND)

    companion object {
        private const val PROJECT_METRIC = "project"
    }
}
