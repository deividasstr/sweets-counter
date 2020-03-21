package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.toConsumedSweet
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
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
        val testVal = DataTestData.TEST_CONSUMED_SWEETMODEL
        val result = Either.Right(Either.None())
        testVal.sweet.target = DataTestData.TEST_SWEETMODEL

        coGiven { db.addSweet(testVal) } willReturn { result }

        sweetsRepo.addSweet(testVal.toConsumedSweet()).getValue() shouldEqual Either.None()
        verify(db).addSweet(testVal)
    }

    @Test
    fun shouldReturnAllAddedSweets() = runBlock {
        val sweet = DataTestData.TEST_CONSUMED_SWEETMODEL
        sweet.sweet.target = DataTestData.TEST_SWEETMODEL

        val sweet2 = DataTestData.TEST_CONSUMED_SWEETMODEL2
        sweet2.sweet.target = DataTestData.TEST_SWEETMODEL2

        coGiven { db.getAllConsumedSweets() } willReturn {
            Either.Right(listOf(sweet, sweet2))
        }

        sweetsRepo.getAllConsumedSweets().getValue() shouldEqual listOf(
            sweet.toConsumedSweet(),
            sweet2.toConsumedSweet())
    }
}