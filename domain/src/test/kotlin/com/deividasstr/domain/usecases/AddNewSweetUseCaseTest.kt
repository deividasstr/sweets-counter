package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AddNewSweetUseCaseTest {

    private lateinit var repo: SweetsRepo
    private lateinit var useCase: AddNewSweetUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(SweetsRepo::class.java)
        useCase = AddNewSweetUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewSweet() {
        val testVal = TestData.TEST_SWEET
        val result = Either.Right(Either.None())

        runBlocking {
            given { runBlocking { repo.newSweet(testVal) } } willReturn { result }

            verify(repo).newSweet(TestData.TEST_SWEET)
            verifyNoMoreInteractions(repo)

            useCase(TestData.TEST_SWEET) { it shouldEqual result }
        }
    }
}