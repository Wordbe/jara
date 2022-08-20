package co.wordbe.issue.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)