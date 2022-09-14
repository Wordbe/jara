package co.wordbe.issue.domain

import co.wordbe.issue.domain.enums.IssuePriority
import co.wordbe.issue.domain.enums.IssueStatus
import co.wordbe.issue.domain.enums.IssueType
import javax.persistence.*

@Entity
@Table
class Issue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: Long,

    var summary: String,

    var description: String,

    @Enumerated(EnumType.STRING)
    var type: IssueType,

    @Enumerated(EnumType.STRING)
    var priority: IssuePriority,

    @Enumerated(EnumType.STRING)
    var status: IssueStatus,

    @OneToMany(mappedBy = "issue", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()

) : BaseEntity() {

    fun isResolved() : Boolean {
        return status == IssueStatus.RESOLVED
    }
}