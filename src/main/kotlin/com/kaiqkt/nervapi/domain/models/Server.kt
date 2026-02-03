package com.kaiqkt.nervapi.domain.models

import io.azam.ulidj.ULID
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "servers")
@EntityListeners(AuditingEntityListener::class)
class Server(
    val name: String = "",
    val url: String = "",
    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: Project = Project(),
) {
    @Id
    val id: String = ULID.random()

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()
}
