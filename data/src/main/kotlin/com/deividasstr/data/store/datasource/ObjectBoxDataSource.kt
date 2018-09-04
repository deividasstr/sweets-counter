package com.deividasstr.data.store.datasource

import androidx.paging.PositionalDataSource
import io.objectbox.query.LazyList

open class ObjectBoxDataSource<T>(
    private val lazyList: LazyList<*>,
    private val converter: (Any) -> T
) : PositionalDataSource<T>() {

    private val size: Int = lazyList.size

    override fun loadInitial(
        params: PositionalDataSource.LoadInitialParams,
        callback: PositionalDataSource.LoadInitialCallback<T>
    ) {

        val fromIndex =
            if (params.requestedStartPosition > params.requestedLoadSize) 0
            else params.requestedStartPosition

        val toIndex = Math.min(params.requestedLoadSize, size)

        callback.onResult(
            lazyList.subList(fromIndex, toIndex).map(converter),
            fromIndex,
            size
        )
    }

    override fun loadRange(
        params: PositionalDataSource.LoadRangeParams,
        callback: PositionalDataSource.LoadRangeCallback<T>
    ) {
        callback.onResult(
            lazyList.subList(
                params.startPosition,
                Math.min(params.startPosition + params.loadSize, size)
            ).map(converter)
        )
    }
}