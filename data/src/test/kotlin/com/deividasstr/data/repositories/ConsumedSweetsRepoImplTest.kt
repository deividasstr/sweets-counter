package com.deividasstr.data.repositories

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.dbs.DbConsumedSweet
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.testutils.TestData
import com.deividasstr.testutils.UnitTest
import com.deividasstr.testutils.coGiven
import com.deividasstr.testutils.runBlock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ConsumedSweetsRepoImplTest : UnitTest() {

    private lateinit var sweetsRepo: ConsumedSweetsRepo

    @Mock
    private lateinit var db: ConsumedSweetsDao

    @Before
    fun setUp() {
        sweetsRepo = ConsumedSweetsRepoImpl(db)
    }

    @Test
    fun shouldAddSweet() = runBlock {
        val expected = TestData.TEST_CONSUMED_SWEET
        val testVal = DbConsumedSweet(expected)
        val result = Either.Right(Either.None())

        coGiven { db.addSweet(testVal) } willReturn { result }

        sweetsRepo.addSweet(expected).getValue() shouldEqual Either.None()
        verify(db).addSweet(testVal)
    }

    @Test
    fun shouldReturnAllAddedSweets() = runBlock {
        val sweet = TestData.TEST_CONSUMED_SWEET
        val sweet2 = TestData.TEST_CONSUMED_SWEET2

        coGiven { db.getAllConsumedSweets() } willReturn {
            Either.Right(listOf(sweet, sweet2))
        }

        sweetsRepo.getAllConsumedSweets().getValue() shouldEqual listOf(
            sweet,
            sweet2
        )
    }
}
