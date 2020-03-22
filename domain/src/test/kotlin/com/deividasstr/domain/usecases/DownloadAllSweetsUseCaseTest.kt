package com.deividasstr.domain.usecases

import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.testutils.UnitTest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DownloadAllSweetsUseCaseTest : UnitTest() {

    @Mock
    private lateinit var repo: SweetsRepo
    private lateinit var useCase: DownloadAllSweetsUseCase

    @Before
    fun setUp() {
        useCase = DownloadAllSweetsUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewSweet() {
        val result = Either.Right(Either.None())

        runBlocking {
            given { runBlocking { repo.downloadAndSaveAllSweets() } } willReturn { result }

            useCase { it shouldEqual result }

            verify(repo, times(1)).downloadAndSaveAllSweets()
            verifyNoMoreInteractions(repo)
        }
    }
}
