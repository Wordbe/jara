package co.wordbe.issue.exception

import java.lang.RuntimeException

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class NotFoundException(
    override val message: String,
) : ServerException(404, message)

data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다",
) : ServerException(401, message)

// sealed class example
//data class SomeNewException(
//    override val message: String = "새로운 예외 입니다.",
//) : ServerException(500, message)
//
//class ErrorManager {
//    private val serverErrorMap = mutableMapOf<Int, ServerException>()
//    fun add(serverException: ServerException) = when (serverException) { // compile error
//        is NotFoundException -> serverErrorMap[serverException.code] = serverException
//        is UnauthorizedException -> serverErrorMap[serverException.code] = serverException
//    }
//}