package com.deividasstr.domain.usecases

import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.PrefsRepo
import com.deividasstr.testutils.UnitTest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SaveDownloadFactDateUseCaseTest : UnitTest() {

    @Mock
    private lateinit var repo: PrefsRepo
    private lateinit var useCase: SaveDownloadFactDateUseCase

    @Before
    fun setUp() {
        useCase = SaveDownloadFactDateUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewFact() {
        val result = Either.Right(Either.None())

        runBlocking {
            given { runBlocking { repo.saveFactsDownloadTime() } } willReturn { result }

            useCase { it shouldEqual result }

            verify(repo).saveFactsDownloadTime()
            verifyNoMoreInteractions(repo)
        }
    }
}
