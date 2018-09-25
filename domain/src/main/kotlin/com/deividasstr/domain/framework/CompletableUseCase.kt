package com.deividasstr.domain.framework

import io.reactivex.Completable

interface CompletableUseCase {
    fun execute(): Completable
}