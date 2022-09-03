package co.wordbe.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)