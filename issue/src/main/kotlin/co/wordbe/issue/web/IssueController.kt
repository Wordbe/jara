package co.wordbe.issue.web

import co.wordbe.issue.config.AuthUser
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.model.IssueRequest
import co.wordbe.issue.model.IssueResponse
import co.wordbe.issue.service.IssueService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ) : IssueResponse =
        issueService.create(authUser.userId, request)

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam(required = false, defaultValue = "TODO") status: IssueStatus,
    ) : List<IssueResponse> =
        issueService.getAll(status)
}