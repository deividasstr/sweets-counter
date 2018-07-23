package com.deividasstr.ui.utils

import androidx.annotation.IntegerRes
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.deividasstr.data.di.modules.DbModule
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.di.modules.SharedPrefsModule
import com.deividasstr.data.store.models.MyObjectBox
import com.deividasstr.ui.base.di.modules.UseCaseModule
import com.deividasstr.ui.utils.di.TestAppComponent
import com.deividasstr.ui.utils.di.TestApplication
import io.objectbox.BoxStore
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.hamcrest.Matchers
import org.junit.runner.RunWith
import retrofit2.Retrofit
import java.io.File

@RunWith(AndroidJUnit4::class)
open class AndroidTest {

    private val networkModule = NetworkModule("https://hello.world.com/api/")

    protected val app: TestApplication
        get() = InstrumentationRegistry.getTargetContext().applicationContext as TestApplication

    fun daggerMockRule(): DaggerMockRule<TestAppComponent> {
        return DaggerMock.rule(
            app,
            networkModule,
            DbModule(),
            SharedPrefsModule(),
            UseCaseModule()) {

            provides { mockServerRetrofit() }
            provides { tempStore() }

            set {
                it.inject(app) // Without this line single tests pass, but multiple tests might not
                app.setComponent(it)
            }
        }
    }

    private fun mockServerRetrofit(): Retrofit {
        return networkModule.provideRetrofit(networkModule.provideInterceptors()).newBuilder()
            .baseUrl(TestVals.mockUrl).build()
    }

    private fun tempStore(): BoxStore {
        val storeFile = File.createTempFile("object-store-test", "")
        storeFile.delete()
        return MyObjectBox.builder().directory(storeFile).build()
    }

    protected fun ActivityTestRule<*>.showsToastWithText(@IntegerRes stringRes: Int) {
        onView(
            ViewMatchers.withText(stringRes))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activity.window.decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}