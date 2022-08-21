package co.wordbe.issue.model

import co.wordbe.issue.domain.Comment
import co.wordbe.issue.domain.Issue
import co.wordbe.issue.domain.enums.IssuePriority
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.domain.enums.IssueType
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

data class IssueResponse(
    val id: Long,
    var userId: Long,
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    val comments: List<CommentResponse> = emptyList(),
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = id!!,
                    userId = userId,
                    summary = summary,
                    description = description,
                    type = type,
                    priority = priority,
                    status = status,
                    comments = comments.sortedByDescending(Comment::id).map(Comment::toResponse),
                    createdAt = createdAt,
                    updatedAt = updatedAt
                )
            }
    }
}