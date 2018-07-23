package com.deividasstr.data.store.utils

import com.deividasstr.data.R
import com.deividasstr.data.utils.StringResException
import io.objectbox.query.Query
import io.objectbox.reactive.DataObserver
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single

object RxObjectBoxQuery {
    /**
     * The returned Flowable emits Query results one by one. Once all results have been processed, onComplete is called.
     * Uses BackpressureStrategy.BUFFER.
     */
    fun <T> flowableOneByOne(query: Query<T>): Flowable<T> {
        return flowableOneByOne(query, BackpressureStrategy.BUFFER)
    }

    /**
     * The returned Flowable emits Query results one by one. Once all results have been processed, onComplete is called.
     * Uses given BackpressureStrategy.
     */
    fun <T> flowableOneByOne(query: Query<T>, strategy: BackpressureStrategy): Flowable<T> {
        return Flowable.create({ emitter -> createListItemEmitter(query, emitter) }, strategy)
    }

    private fun <T> createListItemEmitter(query: Query<T>, emitter: FlowableEmitter<T>) {
        val dataSubscription = query.subscribe().observer(DataObserver<List<T>> { data ->
            for (datum in data) {
                if (emitter.isCancelled) {
                    return@DataObserver
                } else {
                    emitter.onNext(datum)
                }
            }
            if (!emitter.isCancelled) {
                emitter.onComplete()
            }
        })
        emitter.setCancellable { dataSubscription.cancel() }
    }

    /**
     * The returned Single emits one Query result as a List.
     */
    fun <T> singleList(query: Query<T>): Single<List<T>> {
        return Single.create { emitter ->
            query.subscribe().single().observer { data ->
                if (!emitter.isDisposed) {
                    if (data.isEmpty()) {
                        emitter.onError(StringResException(R.string.error_db_no_items))
                    } else {
                        emitter.onSuccess(data)
                    }
                }
            }
        }
    }

    /**
     * The returned Single emits one Query result as an item.
     * Throws NullPointerException on error if result is empty.
     */
    fun <T> singleItem(query: Query<T>): Single<T> {
        return Single.create { emitter ->
            query.subscribe().single().observer { data ->
                if (!emitter.isDisposed) {
                    if (!data.isEmpty()) {
                        emitter.onSuccess(data[0])
                    } else {
                        emitter.onError(NullPointerException("Item not found"))
                    }
                }
            }
        }
    }
}