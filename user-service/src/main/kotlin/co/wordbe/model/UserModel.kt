package co.wordbe.model

import co.wordbe.domain.entity.User
import java.time.LocalDateTime

data class MeResponse(
    val id: Long,
    val email: String,
    val username: String,
    val profileUrl: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(user: User) = with(user) {
            MeResponse(
                id = id!!,
                email = email,
                username = username,
                profileUrl = if (profileUrl.isNullOrEmpty()) null else "https//localhost:9090/images/$profileUrl",
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}