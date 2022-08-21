package co.wordbe.issue.domain

import javax.persistence.*

@Entity
@Table
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    var issue: Issue? = null,

    val userId: Long,

    val username: String,

    var body: String,

) : BaseEntity()