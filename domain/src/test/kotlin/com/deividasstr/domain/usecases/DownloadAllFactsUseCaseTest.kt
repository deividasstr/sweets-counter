package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.FactRepo
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DownloadAllFactsUseCaseTest : UnitTest() {

    @Mock
    private lateinit var repo: FactRepo
    private lateinit var useCase: DownloadAllFactsUseCase

    @Before
    fun setUp() {
        useCase = DownloadAllFactsUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewFact() {
        val result = Either.Right(Either.None())

        given { runBlocking { repo.downloadAllFactsAndSave() } } willReturn { result }
        runBlocking { useCase() { it shouldEqual result } }
    }
}