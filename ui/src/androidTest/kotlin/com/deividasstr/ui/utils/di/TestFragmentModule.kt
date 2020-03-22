package com.deividasstr.ui.utils.di

import com.deividasstr.ui.base.di.modules.FragmentModule
import com.deividasstr.ui.base.di.scopes.sweetdetails.SweetDetailsScope
import com.deividasstr.ui.features.sweetdetails.SweetDetailsFragmentTest
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class TestFragmentModule : FragmentModule() {
    @ContributesAndroidInjector()
    @SweetDetailsScope
    abstract override fun sweetDetailsFragment(): SweetDetailsFragmentTest.TestSweetDetailsFragment
}
