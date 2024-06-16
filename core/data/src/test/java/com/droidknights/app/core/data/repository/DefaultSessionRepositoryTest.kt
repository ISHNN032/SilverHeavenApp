package com.droidknights.app.core.data.repository

import app.cash.turbine.test
import com.droidknights.app.core.data.api.fake.FakeGithubRawApi
import com.droidknights.app.core.data.datastore.fake.FakeSessionPreferencesDataSource
import com.droidknights.app.core.data.repository.api.RecruitRepository
import com.droidknights.app.core.model.Category
import com.droidknights.app.core.model.Recruit
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDateTime

internal class DefaultSessionRepositoryTest : StringSpec() {

    init {
        val repository: RecruitRepository = DefaultRecruitRepository(
            githubRawApi = FakeGithubRawApi(),
            sessionDataSource = FakeSessionPreferencesDataSource()
        )
        "역직렬화 테스트" {
            val expected = Recruit(
                id = "1",
                title = "키노트",
                content = "",
                imageUrl = "",
                companies = emptyList(),
                tags = emptyList(),
                category = Category.ETC,
                startTime = LocalDateTime(2024, 6, 11, 10, 40),
                endTime = LocalDateTime(2024, 6, 11, 11, 0),
                isBookmarked = false
            )
            val actual = repository.getRecruits()
            actual.first() shouldBe expected
        }

        "북마크 추가 테스트" {
            repository.getBookmarkedRecruitIds().test {
                awaitItem() shouldBe emptySet()

                repository.bookmarkRecruit(sessionId = "1", bookmark = true)
                awaitItem() shouldBe setOf("1")

                repository.bookmarkRecruit(sessionId = "2", bookmark = true)
                awaitItem() shouldBe setOf("1", "2")
            }
        }

        "북마크 제거 테스트" {
            // given : [1, 2, 3]
            val bookmarkedSessionIds = listOf("1", "2", "3")
            bookmarkedSessionIds.forEach {
                repository.bookmarkRecruit(it, true)
            }

            repository.getBookmarkedRecruitIds().test {
                awaitItem() shouldBe setOf("1", "2", "3")

                // [1, 2, 3] -> [1, 3]
                repository.bookmarkRecruit(sessionId = "2", bookmark = false)
                awaitItem() shouldBe setOf("1", "3")

                // [1, 3] -> [1]
                repository.bookmarkRecruit(sessionId = "3", bookmark = false)
                awaitItem() shouldBe setOf("1")
            }
        }

        "북마크 일괄 제거 테스트" {
            // given : [1, 2, 3, 4]
            val bookmarkedSessionIds = listOf("1", "2", "3", "4")
            bookmarkedSessionIds.forEach {
                repository.bookmarkRecruit(it, true)
            }

            repository.getBookmarkedRecruitIds().test {
                awaitItem() shouldBe setOf("1", "2", "3", "4")

                // [1, 2, 3, 4] -> [1, 3, 4]
                repository.deleteBookmarkedSessions(setOf("2"))
                awaitItem() shouldBe setOf("1", "3", "4")

                // [1, 3, 4] -> [1]
                repository.deleteBookmarkedSessions(setOf("3", "4"))
                awaitItem() shouldBe setOf("1")
            }
        }
    }
}
