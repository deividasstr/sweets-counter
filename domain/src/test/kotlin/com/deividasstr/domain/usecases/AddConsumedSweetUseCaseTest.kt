package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.willReturn
import org.mockito.Mockito

class AddConsumedSweetUseCaseTest {

    private lateinit var repo: ConsumedSweetsRepo
    private lateinit var useCase: AddConsumedSweetUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(ConsumedSweetsRepo::class.java)
        useCase = AddConsumedSweetUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddConsumedSweet() {
        val testVal = Either.Right(Either.None())
        var result: Either.Right? = null

        runBlocking {
            given { repo.addSweet(TestData.TEST_CONSUMED_SWEET) } willReturn { testVal }
            useCase(TestData.TEST_CONSUMED_SWEET) {  }
            Mockito.verify(repo, Mockito.times(1)).addSweet(TestData.TEST_CONSUMED_SWEET)
            Mockito.verifyNoMoreInteractions(repo)
        }
    }
}