package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.toConsumedSweet
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.common.assertResultValue
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import kotlin.math.roundToInt

class ConsumedSweetsRepoImplTest : UnitTest() {

    private lateinit var sweetsRepo: ConsumedSweetsRepo
    private lateinit var testSubscriber: TestObserver<Any>

    @Mock
    private lateinit var db: ConsumedSweetsDao
    @Mock
    private lateinit var sweetsDb: SweetsDao

    @Before
    fun setUp() {
        sweetsRepo = ConsumedSweetsRepoImpl(db, sweetsDb)
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldAddSweet() {
        val sweet = DataTestData.TEST_CONSUMED_SWEETMODEL
        given { db.addSweet(sweet) }.willReturn(Completable.complete())

        sweetsRepo.addSweet(sweet.toConsumedSweet())
            .subscribe(testSubscriber)

        testSubscriber.await()
        testSubscriber.assertComplete()
    }

    @Test
    fun shouldGetConsumedSweetsByPeriod() {
        given { db.getConsumedSweetsByPeriod(DataTestData.YESTERDAY_TOMORROW_DATERANGE) }
            .willReturn(
                Single.just(
                    listOf(
                        DataTestData.TEST_CONSUMED_SWEETMODEL,
                        DataTestData.TEST_CONSUMED_SWEETMODEL2
                    )
                )
            )

        sweetsRepo.getConsumedSweetsByPeriod(DataTestData.YESTERDAY_TOMORROW_DATERANGE)
            .subscribe(testSubscriber)

        testSubscriber.assertResultValue(
            listOf(
                TestData.TEST_CONSUMED_SWEET,
                TestData.TEST_CONSUMED_SWEET2
            )
        )
    }

    @Test
    fun shouldGetTotalCalsConsumed() {
        given { db.getAllConsumedSweets() }.willReturn(
            Single.just(
                listOf(
                    DataTestData.TEST_CONSUMED_SWEETMODEL,
                    DataTestData.TEST_CONSUMED_SWEETMODEL2
                )
            )
        )

        given { sweetsDb.getSweetById(2) }.willReturn(Single.just(DataTestData.TEST_SWEETMODEL))
        given { sweetsDb.getSweetById(3) }.willReturn(Single.just(DataTestData.TEST_SWEETMODEL2))

        sweetsRepo.getTotalCalsConsumed().subscribe(testSubscriber)

        val result =
            ((DataTestData.TEST_CONSUMED_SWEETMODEL.g * DataTestData.TEST_SWEETMODEL.calsPer100 / 100) +
                (DataTestData.TEST_CONSUMED_SWEETMODEL2.g * DataTestData.TEST_SWEETMODEL2.calsPer100 / 100))
                    .roundToInt()

        testSubscriber.assertResultValue(result)
    }

    @Test
    fun shouldGetTotalCalsConsumed_returnsZero() {
        given { db.getAllConsumedSweets() }.willReturn(Single.just(emptyList()))

        sweetsRepo.getTotalCalsConsumed().subscribe(testSubscriber)

        testSubscriber.assertResultValue(0)
    }
}