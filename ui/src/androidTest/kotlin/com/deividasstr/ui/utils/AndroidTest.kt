package com.deividasstr.ui.utils

import android.view.View
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
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
import java.io.File
import java.util.concurrent.TimeoutException
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers.containsString
import org.hamcrest.TypeSafeMatcher
import org.junit.Ignore
import org.junit.runner.RunWith
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
@Ignore
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

    protected fun showsSnackWithText(@IntegerRes stringRes: Int) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(stringRes)))
    }

    protected fun showsSnackWithText(string: String) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(string)))
    }

    /** Perform action of waiting for a specific view id.  */
    fun waitId(viewId: Int, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $millis millis."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                // timeout happens
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }

    protected fun Int.containsText(text: String) {
        onView(withId(this)).check(matches(withText(containsString(text))))
    }

    protected fun Int.tickerContainsText(text: String) {
        onView(withId(this)).check(matches(CustomMatchers.tickerWithText(containsString(text))))
    }

    protected fun Int.backgroundColor(@IntegerRes color: Int) {
        onView(withId(this)).check(matches(CustomMatchers.hasColor(color)))
    }

    protected fun Int.type(text: String) {
        onView(withId(this)).perform(typeText(text), closeSoftKeyboard())
    }

    protected fun Int.click() {
        onView(withId(this)).perform(ViewActions.click())
    }

    protected fun countRecyclerViewItems(recyclerViewId: Int): Int {
        val count = intArrayOf(0)
        val matcher = object : TypeSafeMatcher<View>() {
            override fun describeTo(description: org.hamcrest.Description?) {}

            override fun matchesSafely(item: View): Boolean {
                count[0] = (item as RecyclerView).adapter!!.itemCount
                return true
            }
        }
        onView(CoreMatchers.allOf(withId(recyclerViewId), ViewMatchers.isDisplayed()))
            .check(matches(matcher))
        return count[0]
    }

    protected fun Int.nthItemHasText(pos: Int, text: String) {
        onView(CustomMatchers.nthChildOf(withId(this), pos)).check(
            matches((ViewMatchers.hasDescendant(withText(containsString(text))))))
    }
}
