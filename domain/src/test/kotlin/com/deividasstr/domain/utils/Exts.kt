package com.deividasstr.domain.utils

import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.mockito.BDDMockito
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> coGiven(methodCall: suspend () -> T): BDDMockito.BDDMyOngoingStubbing<T> {
    return given(runBlocking { methodCall() })
}

@Throws(InterruptedException::class)
fun <T> runBlock(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T) {
    runBlocking(context, block)
}