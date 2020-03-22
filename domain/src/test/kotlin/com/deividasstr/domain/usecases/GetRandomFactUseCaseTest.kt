package com.deividasstr.domain.usecases

import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.testutils.TestData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetRandomFactUseCaseTest {

    private lateinit var repo: FactRepo
    private lateinit var useCase: GetRandomFactUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(FactRepo::class.java)
        useCase = GetRandomFactUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFact() {
        val testVal = TestData.TEST_ID
        val result = Either.Right(TestData.TEST_FACT_1)

        runBlocking {
            given { runBlocking { repo.getRandomFact(testVal) } } willReturn { result }

            useCase(TestData.TEST_ID) { it shouldEqual result }

            verify(repo).getRandomFact(testVal)
            verifyNoMoreInteractions(repo)
        }
    }
}
