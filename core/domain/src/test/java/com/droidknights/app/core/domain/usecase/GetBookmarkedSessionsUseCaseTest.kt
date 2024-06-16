package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.model.Category
import com.droidknights.app.core.model.Recruit
import com.droidknights.app.core.model.Company
import com.droidknights.app.core.model.Tag
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.single
import kotlinx.datetime.LocalDateTime

internal class GetBookmarkedSessionsUseCaseTest : BehaviorSpec() {

    private val fakeSessionsRepository = FakeRecruitRepository(
        bookmarkedSessionIds = bookmarkedSessionIds,
        recruits = recruits
    )

    private val useCase: GetBookmarkedSessionsUseCase = GetBookmarkedSessionsUseCase(
        getSessionsUseCase = GetSessionsUseCase(recruitRepository = fakeSessionsRepository),
        getBookmarkedSessionIdsUseCase = GetBookmarkedSessionIdsUseCase(recruitRepository = fakeSessionsRepository)
    )

    init {
        Given("북마크된 아이템이 존재한다") {
            val expected = bookmarkedSessionIds

            When("북마크된 SessionId을 가진 Session들을 조회한다") {
                val bookmarkedSessions = useCase().single()

                Then("북마크된 세션들을 반환한다") {
                    bookmarkedSessions.size shouldBe 2
                    bookmarkedSessions.map { it.id } shouldContainAll expected
                }

                Then("북마크된 세션들을 시작시간이 빠른 순으로 정렬하여 반환한다") {
                    bookmarkedSessions shouldBeSortedWith { left, right ->
                        left.startTime.compareTo(
                            right.startTime
                        )
                    }
                }
            }
        }
    }

    companion object {
        private val bookmarkedSessionIds = setOf("1", "2")
        private val recruits = listOf(
            Recruit(
                id = "3",
                title = "Item3 Title",
                content = "Item3 Content",
                speakers = listOf(Company(name = "철수", introduction = "", imageUrl = "")),
                tags = listOf(Tag(name = "Architecture")),
                category = Category.JOB,
                startTime = LocalDateTime(2023, 10, 5, 11, 0),
                endTime = LocalDateTime(2023, 10, 5, 11, 50),
                isBookmarked = false
            ),
            Recruit(
                id = "1",
                title = "Item1 Title",
                content = "Item1 Content",
                speakers = listOf(Company(name = "영희", introduction = "", imageUrl = "")),
                tags = listOf(Tag(name = "Architecture")),
                category = Category.JOB,
                startTime = LocalDateTime(2023, 10, 5, 9, 0),
                endTime = LocalDateTime(2023, 10, 5, 9, 50),
                isBookmarked = false
            ),
            Recruit(
                id = "2",
                title = "Item2 Title",
                content = "Item2 Content",
                speakers = listOf(Company(name = "민수", introduction = "", imageUrl = "")),
                tags = listOf(Tag(name = "Architecture")),
                category = Category.JOB,
                startTime = LocalDateTime(2023, 10, 5, 10, 0),
                endTime = LocalDateTime(2023, 10, 5, 10, 50),
                isBookmarked = false
            )
        )
    }
}
