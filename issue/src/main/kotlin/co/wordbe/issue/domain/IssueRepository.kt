package co.wordbe.issue.domain

import co.wordbe.issue.domain.enums.IssueStatus
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.*
import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository : JpaRepository<Issue, Long> {

    @EntityGraph(type = LOAD, attributePaths = ["comments"])
    fun findAllByStatusOrderByCreatedAtDesc(status: IssueStatus): List<Issue>
}