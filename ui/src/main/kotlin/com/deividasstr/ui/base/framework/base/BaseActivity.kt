package com.deividasstr.ui.base.framework.base

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.extensions.alert
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun setFab(fabSetter: FabSetter?)
    abstract fun liftNavBar()

    fun alert(stringRes: Int) { container.alert(stringRes) }
    fun alert(string: String) { container.alert(string) }
}

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}
