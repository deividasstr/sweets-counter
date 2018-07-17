package com.deividasstr.ui.base.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {

    abstract fun getViewModelClass(): Class<VM>

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun layoutId(): Int

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModelClass()]
        observe(viewModel.errorMessage) {
            it?.getContentIfNotHandled()?.let {
                context?.alert(getString(it.messageStringRes))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return binding.root
    }
}
