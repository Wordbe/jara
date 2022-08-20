package co.wordbe.issue.service

import co.wordbe.issue.config.AuthUser
import co.wordbe.issue.domain.Issue
import co.wordbe.issue.domain.IssueRepository
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.model.IssueRequest
import co.wordbe.issue.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {

    @Transactional
    fun create(userId: Long, request: IssueRequest) : IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )
        return IssueResponse(issueRepository.save(issue))
    }

    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            .map { IssueResponse(it) }
}