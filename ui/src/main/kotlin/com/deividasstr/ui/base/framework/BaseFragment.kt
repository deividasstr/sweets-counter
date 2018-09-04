package com.deividasstr.ui.base.framework

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
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@DebugOpenClass
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {

    abstract fun getViewModelClass(): Class<VM>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun layoutId(): Int

    abstract val fabSetter: FabSetter?

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModelClass()]
        observe(viewModel.errorMessage) { it ->
            it?.getContentIfNotHandled()?.let {
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
