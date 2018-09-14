package com.deividasstr.ui.base.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.extensions.observe
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@DebugOpenClass
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {

    abstract fun getViewModelClass(): Class<VM>
    abstract fun layoutId(): Int

    abstract val fabSetter: FabSetter?

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModelClass()]
        observe(viewModel.errorMessage) { it ->
            it.getContentIfNotHandled()?.let {
                (activity as BaseActivity).alert(it.messageStringRes)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(layoutId(), container, false)
        binding = DataBindingUtil.bind(view)!!
        binding.setLifecycleOwner(this)
        (activity as BaseActivity).setFab(fabSetter)
        return view
    }

    fun navController() = findNavController(view!!)
}
