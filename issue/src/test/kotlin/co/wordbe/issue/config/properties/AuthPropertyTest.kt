package co.wordbe.issue.config.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AuthPropertyTest {
    @Autowired
    lateinit var authProperty: AuthProperty

    @Test
    fun `The auth string is correct`() {
        assertThat(authProperty.url).isEqualTo("http://localhost:9090/api/v1/users/me")
    }
}