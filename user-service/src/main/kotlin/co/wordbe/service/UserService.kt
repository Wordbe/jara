package co.wordbe.service

import co.wordbe.config.JWTProperties
import co.wordbe.domain.entity.User
import co.wordbe.domain.UserRepository
import co.wordbe.exception.InvalidJWTException
import co.wordbe.exception.PasswordNotMatchedException
import co.wordbe.exception.UserExistsException
import co.wordbe.exception.UserNotFoundException
import co.wordbe.model.SignInRequest
import co.wordbe.model.SignInResponse
import co.wordbe.model.SignUpRequest
import co.wordbe.utils.BCryptUtils
import co.wordbe.utils.JWTClaim
import co.wordbe.utils.JWTUtils
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

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

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(signInRequest.password, password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

    suspend fun getByToken(token: String): User {
        val cachedUser: User =  cacheManager.awaitGetOrPut(key = token, ttl = CACHE_TTL) {
            // ????????? ???????????? ?????? ?????? ??????
            val decodedJWT: DecodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)
            val userId: Long = decodedJWT.claims["userId"]?.asLong() ?: throw InvalidJWTException()
            getUser(userId)
        }

        return cachedUser
    }

    suspend fun getUser(userId: Long): User {
        return userRepository.findById(userId) ?: throw UserNotFoundException()
    }

    suspend fun edit(token: String, username: String?, profileUrl: String?): User {
        val user = getByToken(token)
        val newUser = user.copy(
            username = username ?: "????????????",
            profileUrl = profileUrl ?: user.profileUrl
        )

        return userRepository.save(newUser).also {
            cacheManager.awaitPut(key = token, value = it, ttl = CACHE_TTL)
        }
    }
}