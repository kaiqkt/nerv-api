package com.kaiqkt.nervapi.domain.repositories

import com.kaiqkt.nervapi.domain.models.Server
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServerRepository : JpaRepository<Server, String>
