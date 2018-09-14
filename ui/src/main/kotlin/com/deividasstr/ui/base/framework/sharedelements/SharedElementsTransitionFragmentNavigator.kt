package com.deividasstr.ui.base.framework.sharedelements

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.ArrayDeque

/**
 * From https://github.com/lion4ik/aac-navigation-shared-elements-transition
 *
 * All the content copied from @FragmentNavigator (Navigation v1.0.0-alpha05). The only change is in
 * {@link androidx.navigation.fragment.FragmentNavigator#navigate(FragmentNavigator.Destination, Bundle,NavOptions) navigate}
 * method, where shared elements transition is added.
 */
@Navigator.Name("fragment")
class SharedElementsTransitionFragmentNavigator constructor(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) :
    Navigator<FragmentNavigator.Destination>() {

    private val TAG = "FragmentNavigator"
    private val KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds"

    private var mBackStack = ArrayDeque<Int>()
    private var mIsPendingBackStackOperation = false

    private val mOnBackStackChangedListener =
        FragmentManager.OnBackStackChangedListener {
            if (mIsPendingBackStackOperation) {
                mIsPendingBackStackOperation = !isBackStackEqual()
                return@OnBackStackChangedListener
            }

            val newCount = fragmentManager.backStackEntryCount + 1
            if (newCount < mBackStack.size) {
                while (mBackStack.size > newCount) {
                    mBackStack.removeLast()
                }
                val destId = if (mBackStack.isEmpty()) 0 else mBackStack.peekLast()
                dispatchOnNavigatorNavigated(destId, Navigator.BACK_STACK_DESTINATION_POPPED)
            }
        }

    override fun onActive() {
        fragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener)
    }

    override fun onInactive() {
        fragmentManager.removeOnBackStackChangedListener(mOnBackStackChangedListener)
    }

    override fun popBackStack(): Boolean {
        if (mBackStack.isEmpty()) {
            return false
        }
        if (fragmentManager.isStateSaved) {
            return false
        }
        var popped = false
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            mIsPendingBackStackOperation = true
            popped = true
        }
        mBackStack.removeLast()
        val destId = if (mBackStack.isEmpty()) 0 else mBackStack.peekLast()
        dispatchOnNavigatorNavigated(destId, Navigator.BACK_STACK_DESTINATION_POPPED)
        return popped
    }

    override fun createDestination(): FragmentNavigator.Destination {
        return FragmentNavigator.Destination(this)
    }

    override fun navigate(
        destination: FragmentNavigator.Destination,
        args: Bundle?,
        navOptions: NavOptions?
    ) {
        if (fragmentManager.isStateSaved) {
            return
        }
        val frag = destination.createFragment(args)
        val ft = fragmentManager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        val currentFrag = fragmentManager.findFragmentById(containerId)
        if (currentFrag is HasSharedElements) {
            val sharedElements = currentFrag.getSharedElements()
            ft.setReorderingAllowed(currentFrag.hasReorderingAllowed())
            for ((key, value) in sharedElements) {
                ft.addSharedElement(value, key)
            }
        } else {
            ft.setReorderingAllowed(true)
        }

        ft.replace(containerId, frag)
        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        val isClearTask = navOptions != null && navOptions.shouldClearTask()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation &&
            navOptions.shouldLaunchSingleTop() &&
            mBackStack.peekLast() == destId)

        val backStackEffect: Int
        if (initialNavigation || isClearTask) {
            backStackEffect = Navigator.BACK_STACK_DESTINATION_ADDED
        } else if (isSingleTopReplacement) {
            if (mBackStack.size > 1) {
                fragmentManager.popBackStack()
                ft.addToBackStack(Integer.toString(destId))
                mIsPendingBackStackOperation = true
            }
            backStackEffect = Navigator.BACK_STACK_UNCHANGED
        } else {
            ft.addToBackStack(Integer.toString(destId))
            mIsPendingBackStackOperation = true
            backStackEffect = Navigator.BACK_STACK_DESTINATION_ADDED
        }
        ft.commit()
        if (backStackEffect == Navigator.BACK_STACK_DESTINATION_ADDED) {
            mBackStack.add(destId)
        }
        dispatchOnNavigatorNavigated(destId, backStackEffect)
    }

    override fun onSaveState(): Bundle? {
        val b = Bundle()
        val backStack = IntArray(mBackStack.size)
        var index = 0
        for (id in mBackStack) {
            backStack[index++] = id!!
        }
        b.putIntArray(KEY_BACK_STACK_IDS, backStack)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        val backStack = savedState.getIntArray(KEY_BACK_STACK_IDS)
        if (backStack != null) {
            mBackStack.clear()
            for (destId in backStack) {
                mBackStack.add(destId)
            }
        }
    }

    private fun isBackStackEqual(): Boolean {
        val fragmentBackStackCount = fragmentManager.backStackEntryCount
        if (mBackStack.size != fragmentBackStackCount + 1) {
            return false
        }

        val backStackIterator = mBackStack.descendingIterator()
        var fragmentBackStackIndex = fragmentBackStackCount - 1
        while (backStackIterator.hasNext() && fragmentBackStackIndex >= 0) {
            val destId = backStackIterator.next()
            val fragmentDestId = Integer.valueOf(
                fragmentManager.getBackStackEntryAt(fragmentBackStackIndex--).name!!)
            if (destId != fragmentDestId) {
                return false
            }
        }

        return true
    }
}