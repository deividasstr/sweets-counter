package com.deividasstr.domain.usecases

import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo
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

class GetSweetByIdUseCaseTest {

    private lateinit var repo: SweetsRepo
    private lateinit var useCase: GetSweetByIdUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(SweetsRepo::class.java)
        useCase = GetSweetByIdUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnSweet() {
        val testVal = TestData.TEST_ID
        val result = Either.Right(TestData.TEST_SWEET)

        runBlocking {
            given { runBlocking { repo.getSweetById(testVal) } } willReturn { result }

            useCase(testVal) { it shouldEqual result }

            verify(repo).getSweetById(testVal)
            verifyNoMoreInteractions(repo)
        }
    }
}
