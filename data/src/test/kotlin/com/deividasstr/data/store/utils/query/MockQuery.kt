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

package com.deividasstr.data.store.utils.query

import com.nhaarman.mockito_kotlin.mock
import io.objectbox.query.Query
import io.objectbox.reactive.SubscriptionBuilder
import org.mockito.Mockito.`when`
import java.util.concurrent.Executors

class MockQuery<T> {
    var query: Query<T> = mock()
    var fakeQueryPublisher: FakeQueryPublisher<T> = FakeQueryPublisher()

    init {
        val subscriptionBuilder =
            SubscriptionBuilder(fakeQueryPublisher, null, Executors.newFixedThreadPool(1))
        `when`(query.subscribe()).thenReturn(subscriptionBuilder)
    }
}
