package com.kaiqkt.nervapi.domain.repositories

import com.kaiqkt.nervapi.domain.models.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, String> {
    fun existsByName(name: String): Boolean
}
