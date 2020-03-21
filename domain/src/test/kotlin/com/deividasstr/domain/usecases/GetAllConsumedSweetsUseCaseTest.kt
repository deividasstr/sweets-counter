package com.deividasstr.domain.usecases

import com.deividasstr.testutils.UnitTest
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.testutils.TestData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
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
        val testVal = TestData.TEST_LIST_CONSUMED_SWEETS2
        val result = Either.Right(testVal)

        runBlocking {
            given { runBlocking { repo.getAllConsumedSweets() } } willReturn { result }

            useCase { it shouldEqual result }

            verify(repo).getAllConsumedSweets()
            verifyNoMoreInteractions(repo)
        }
    }
}