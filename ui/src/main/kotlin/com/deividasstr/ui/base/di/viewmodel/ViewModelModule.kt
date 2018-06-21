/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deividasstr.ui.base.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.deividasstr.ui.features.addconsumedsweet.AddConsumedSweetViewModel
import com.deividasstr.ui.features.facts.FactsViewModel
import com.deividasstr.ui.features.splash.SplashActivityViewModel
import com.deividasstr.ui.features.perioddetails.PeriodDetailsViewModel
import com.deividasstr.ui.features.sweetdetails.SweetDetailsViewModel
import com.deividasstr.ui.features.sweetsearchlist.SweetsSearchListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FactsViewModel::class)
     abstract fun bindsFactsViewModel(factsViewModel: FactsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddConsumedSweetViewModel::class)
    internal abstract fun bindsAddConsumedSweetViewModel(addConsumedSweetViewModel: AddConsumedSweetViewModel)
        : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeriodDetailsViewModel::class)
    internal abstract fun bindsPeriodDetailsViewModel(periodDetailsViewModel: PeriodDetailsViewModel)
        : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SweetDetailsViewModel::class)
    internal abstract fun bindsSweetDetailsViewModel(sweetDetailsViewModel: SweetDetailsViewModel)
        : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SweetsSearchListViewModel::class)
    internal abstract fun bindsSweetsSearchListViewModel(sweetsSearchListViewModel: SweetsSearchListViewModel)
        : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashActivityViewModel::class)
    internal abstract fun bindsSplashActivityViewModel(splashActivityViewModel: SplashActivityViewModel)
        : ViewModel
}