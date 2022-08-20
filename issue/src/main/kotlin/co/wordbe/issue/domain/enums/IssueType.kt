package co.wordbe.issue.domain.enums

enum class IssueType {
    BUG,
    TASK;

    companion object {
        operator fun invoke(type: String) = valueOf(type.uppercase()) // 함수명 없이 생성자처럼 사용할 수 있다.
    }
}
