package co.wordbe.issue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class IssueApplication

fun main(args: Array<String>) {
	runApplication<IssueApplication>(*args)
}
