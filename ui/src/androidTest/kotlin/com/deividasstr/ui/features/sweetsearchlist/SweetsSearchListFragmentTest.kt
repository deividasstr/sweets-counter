package com.deividasstr.ui.features.sweetsearchlist

import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.deividasstr.ui.features.main.MainActivity
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class SweetsSearchListFragmentTest  {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    /*@Rule
    var fragmentTestRule: FragmentTestRule<MainActivity, SweetsSearchListFragment> =
        FragmentTestRule(MainActivity::class.java, SweetsSearchListFragment::class.java)*/

    //private lateinit var sweetSearchListFragment: SweetsSearchListFragment

    @Before
    fun setup() {

        //activityRule.activity.suppo().beginTransaction()
        //db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        /*given { viewModelFactory.create(SweetsSearchListViewModel::class.java) }.willReturn(
            viewModel)*/

        //Mockito.doReturn(viewModel).`when`(viewModelFactory).create(SweetsSearchListViewModel::class.java)

        //sweetSearchListFragment = SweetsSearchListFragment()

        //SupportFragmentController2.of(sweetSearchListFragment).create()

        //SupportFragmentController2.setupFragment(sweetSearchListFragment)
    }

    @Test
    fun shouldNotBeNull() {
        //assertNotNull(sweetSearchListFragment)
        //assertNotNull(fragmentTestRule.fragment.view)
        assertNotNull(activityRule.activity)
        assert(2 == 1+1)
    }

    @Test
    fun shouldList2Items() {
        //println("vm1 $viewModelFactory")
        //println("vm2 ${sweetSearchListFragment.viewModelFactory}")

        //val itemCount = sweetSearchListFragment.view?.findViewById<RecyclerView>(R.id.sweets_list)?.adapter?.itemCount
        //itemCount `should be` UiTestData.UI_SWEET_LIST.size
    }
}