package com.deividasstr.domain.common

import io.reactivex.observers.TestObserver

fun <T> TestObserver<T>.assertResultValue(value: T) {
    await()
    assertComplete()
    assertValue(value)
}