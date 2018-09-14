package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetAllConsumedSweetsUseCaseTest : UnitTest() {

    @Mock
    private lateinit var repo: ConsumedSweetsRepo
    private lateinit var useCase: GetAllConsumedSweetsUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        useCase = GetAllConsumedSweetsUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetAllConsumedSweets() {
        given { repo.getAllConsumedSweets() }.willReturn(Single.just(TestData.TEST_LIST_CONSUMED_SWEETS2))
        useCase.execute().test().assertComplete().assertValue(TestData.TEST_LIST_CONSUMED_SWEETS2)
    }
}