package co.wordbe.issue.service

import co.wordbe.issue.domain.Issue
import co.wordbe.issue.domain.IssueRepository
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.exception.NotFoundException
import co.wordbe.issue.model.IssueRequest
import co.wordbe.issue.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
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

    fun get(id: Long): IssueResponse {
        val issue = findByIdOrThrow(id)
        return IssueResponse(issue)
    }

    @Transactional
    fun edit(userId: Long, id: Long, request: IssueRequest): IssueResponse {
        val issue = findByIdOrThrow(id)

        return with(issue) {
            this.userId = userId
            summary = request.summary
            description = request.description
            type = request.type
            priority = request.priority
            status = request.status

            IssueResponse(issueRepository.save(this))
        }
    }

    @Transactional
    fun delete(id: Long) {
        issueRepository.deleteById(id)
    }

    private fun findByIdOrThrow(id: Long) = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")
}