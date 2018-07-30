package com.deividasstr.utils

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class AsyncTaskSchedulerRule : TestWatcher() {
    private val asyncTaskScheduler = Schedulers.trampoline()

    override fun starting(description: Description?) {
        RxJavaPlugins.setIoSchedulerHandler { asyncTaskScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { asyncTaskScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { asyncTaskScheduler }
    }

    override fun finished(description: Description?) = RxJavaPlugins.reset()
}