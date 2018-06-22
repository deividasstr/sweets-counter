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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deividasstr.ui.features.addconsumedsweet.AddConsumedSweetViewModel
import com.deividasstr.ui.features.consumedsweetdata.ConsumedSweetDataViewModel
import com.deividasstr.ui.features.facts.FactsViewModel
import com.deividasstr.ui.features.main.MainActivityViewModel
import com.deividasstr.ui.features.sweetdetails.SweetDetailsViewModel
import com.deividasstr.ui.features.sweethistory.ConsumedSweetHistoryViewModel
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
    @ViewModelKey(ConsumedSweetHistoryViewModel::class)
    internal abstract fun bindsConsumedSweetHistoryViewModel(consumedSweetHistoryViewModel: ConsumedSweetHistoryViewModel)
        : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConsumedSweetDataViewModel::class)
    internal abstract fun bindsConsumedSweetDataViewModel(consumedSweetDataViewModel: ConsumedSweetDataViewModel)
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
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindsSplashActivityViewModel(mainActivityViewModel: MainActivityViewModel)
        : ViewModel
}