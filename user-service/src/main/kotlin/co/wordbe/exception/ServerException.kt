package co.wordbe.exception

import java.lang.RuntimeException

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class UserExistsException(
    override val message: String = "이미 존재하는 사용자입니다"
) : ServerException(409, message)

data class UserNotFoundException(
    override val message: String = "사용자가 존재하지 않습니다"
) : ServerException(404, message)

data class PasswordNotMatchedException(
    override val message: String = "패스워드가 잘못되었습니다"
) : ServerException(400, message)

data class InvalidJWTException(
    override val message: String = "유효하지 않은 토큰입니다"
) : ServerException(400, message)