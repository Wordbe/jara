package co.wordbe.issue.web

import co.wordbe.issue.config.AuthUser
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.model.CommentRequest
import co.wordbe.issue.model.CommentResponse
import co.wordbe.issue.model.IssueRequest
import co.wordbe.issue.model.IssueResponse
import co.wordbe.issue.service.CommentService
import co.wordbe.issue.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest,
    ) : CommentResponse =
        commentService.create(issueId, authUser.userId, authUser.username, request)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @PathVariable id: Long,
        @RequestBody request: CommentRequest,
    ) : CommentResponse? =
        commentService.edit(id, authUser.userId, request)
}