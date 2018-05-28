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

import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataPublisher
import io.objectbox.reactive.DataPublisherUtils
import java.util.concurrent.CopyOnWriteArraySet

class FakeQueryPublisher<T> : DataPublisher<List<T>> {

    private val observers: Set<DataObserver<List<T>>> = CopyOnWriteArraySet()

    var queryResult = emptyList<T>()

    @Synchronized
    override fun subscribe(observer: DataObserver<List<T>>, param: Any?) {
        observers.plus(observer)
    }

    override fun publishSingle(observer: DataObserver<List<T>>, param: Any?) {
        observer.onData(queryResult)
    }

    fun publish() {
        for (observer in observers) {
            observer.onData(queryResult)
        }
    }

    @Synchronized
    override fun unsubscribe(observer: DataObserver<List<T>>, param: Any?) {
        DataPublisherUtils.removeObserverFromCopyOnWriteSet(observers, observer)
    }
}
