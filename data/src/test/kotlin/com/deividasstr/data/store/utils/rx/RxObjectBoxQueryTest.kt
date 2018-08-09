/*
 * Copyright (C) 2017 greenrobot/ObjectBox (http://greenrobot.org)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deividasstr.data.store.utils.rx

import com.deividasstr.data.store.utils.RxObjectBoxQuery
import com.deividasstr.data.store.utils.query.MockQuery
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@RunWith(MockitoJUnitRunner::class)
class RxObjectBoxQueryTest : Observer<List<String>>, SingleObserver<List<String>>, Consumer<String> {

    private val receivedChanges = CopyOnWriteArrayList<List<String>>()
    private val receivedChangesItem = CopyOnWriteArrayList<Any>()
    private var latch = CountDownLatch(1)

    private val mockQuery = MockQuery<String>()
    private val publisher = mockQuery.fakeQueryPublisher
    private val listResult = ArrayList<String>()
    private var error: Throwable? = null

    private val completedCount = AtomicInteger()

    private lateinit var observer: SingleObserver<Any>

    @Before
    fun prep() {
        listResult.add("foo")
        listResult.add("bar")

        observer = object : SingleObserver<Any> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onSuccess(s: Any) {
                receivedChangesItem.add(s)
                latch.countDown()
            }

            override fun onError(e: Throwable) {
                error = e
            }
        }
    }

    @Test
    fun testFlowableOneByOne() {
        publisher.queryResult = listResult

        latch = CountDownLatch(2)
        val flowable = RxObjectBoxQuery.flowableOneByOne(mockQuery.query)
        flowable.subscribe(this as Consumer<in String>)
        assertLatchCountedDown(latch, 2)
        assertEquals(2, receivedChanges.size.toLong())
        assertEquals(1, receivedChanges[0].size.toLong())
        assertEquals(1, receivedChanges[1].size.toLong())
        assertNull(error)

        receivedChanges.clear()
        publisher.publish()
        assertNoMoreResults()
    }

    @Test
    fun testSingle() {
        publisher.queryResult = listResult
        val single = RxObjectBoxQuery.singleList(mockQuery.query)
        single.subscribe(this as SingleObserver<in List<String>>)
        assertLatchCountedDown(latch, 2)
        assertEquals(1, receivedChanges.size.toLong())
        assertEquals(2, receivedChanges[0].size.toLong())

        receivedChanges.clear()
        publisher.publish()
        assertNoMoreResults()
    }

    @Test
    fun testSingleItem() {
        val list = ArrayList<String>()
        list.add("foo")
        publisher.queryResult = list
        RxObjectBoxQuery.singleItem(mockQuery.query).subscribe(observer)
        assertLatchCountedDown(latch, 2)
        assertNull(error)
        assertEquals(1, receivedChangesItem.size.toLong())
        assertEquals("foo", receivedChangesItem[0])

        receivedChangesItem.clear()
        publisher.publish()
        assertNoMoreResults()
    }

    @Test
    fun testSingleItem_returnsError() {
        publisher.queryResult = ArrayList()
        val single = RxObjectBoxQuery.singleItem(mockQuery.query)
        single.subscribe(observer)
        assertTrue(error != null && error is NullPointerException)
        assertEquals(0, receivedChangesItem.size.toLong())

        receivedChangesItem.clear()
        publisher.publish()
        assertNoMoreResults()
    }

    private fun assertNoMoreResults() {
        assertEquals(0, receivedChanges.size.toLong())
        try {
            Thread.sleep(20)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        assertEquals(0, receivedChanges.size.toLong())
    }

    private fun assertLatchCountedDown(latch: CountDownLatch, seconds: Int) {
        try {
            assertTrue(latch.await(seconds.toLong(), TimeUnit.SECONDS))
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(queryResult: List<String>) {
        receivedChanges.add(queryResult)
        latch.countDown()
    }

    override fun onNext(queryResult: List<String>) {
        receivedChanges.add(queryResult)
        latch.countDown()
    }

    override fun onError(e: Throwable) {
        error = e
    }

    override fun onComplete() {
        completedCount.incrementAndGet()
    }

    @Throws(Exception::class)
    override fun accept(@NonNull s: String) {
        receivedChanges.add(listOf(s))
        latch.countDown()
    }
}
