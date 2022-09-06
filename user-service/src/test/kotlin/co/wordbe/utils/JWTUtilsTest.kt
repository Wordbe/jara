package co.wordbe.utils

import co.wordbe.config.JWTProperties
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class JWTUtilsTest {
    private val logger = KotlinLogging.logger {}

    @Test
    fun createToken() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            profileUrl = "profile.jpg",
            username = "개발자"
        )

        val properties = JWTProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtils.createToken(jwtClaim, properties)

        assertNotNull(token)

        logger.info { "token: $token" }
    }

    @Test
    fun decode() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            profileUrl = "profile.jpg",
            username = "개발자"
        )

        val properties = JWTProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtils.createToken(jwtClaim, properties)

        val decode = JWTUtils.decode(token, properties.secret, properties.issuer)

        with(decode) {
            logger.info { "claim: $claims" }

            val userId = claims["userId"]!!.asLong()
            val email = claims["email"]!!.asString()
            val profileUrl = claims["profileUrl"]!!.asString()
            val username = claims["username"]!!.asString()

            assertEquals(userId, jwtClaim.userId)
            assertEquals(email, jwtClaim.email)
            assertEquals(profileUrl, jwtClaim.profileUrl)
            assertEquals(username, jwtClaim.username)
        }
    }
}