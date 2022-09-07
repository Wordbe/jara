package co.wordbe.service

import co.wordbe.domain.entity.User
import co.wordbe.domain.entity.UserRepository
import co.wordbe.exception.UserExistsException
import co.wordbe.model.SignUpRequest
import co.wordbe.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }
            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )
            userRepository.save(user)
        }
    }
}