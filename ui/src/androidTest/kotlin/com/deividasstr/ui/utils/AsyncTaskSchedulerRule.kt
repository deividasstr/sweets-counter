package com.deividasstr.ui.utils

import android.os.AsyncTask
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class AsyncTaskSchedulerRule : TestWatcher() {
    private val asyncTaskScheduler = Schedulers.from(
        AsyncTask.THREAD_POOL_EXECUTOR)

    override fun starting(description: Description?) {
        RxJavaPlugins.setIoSchedulerHandler { asyncTaskScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { asyncTaskScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { asyncTaskScheduler }
    }

    override fun finished(description: Description?) = RxJavaPlugins.reset()
}