package com.deividasstr.ui.base.framework

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

object MainThreadExecutor : Executor {
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mainHandler.post(command)
    }
}