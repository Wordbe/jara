package co.wordbe.issue.domain

import co.wordbe.issue.domain.enums.IssuePriority
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.domain.enums.IssueType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class IssueTest {

    @Test
    fun `An issue is resolved`() {
        val issue = Issue(
            userId = 1,
            status = IssueStatus.RESOLVED,
            summary = "이슈 요약",
            description = "이슈 설명",
            type = IssueType.TASK,
            priority = IssuePriority.MEDIUM,
        )

        assertTrue(issue.isResolved())
    }
}