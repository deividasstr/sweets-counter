package com.deividasstr.ui.base.di.modules

import com.deividasstr.ui.base.di.scopes.addconsumedsweet.AddConsumedSweetScope
import com.deividasstr.ui.base.di.scopes.consumedsweetdata.ConsumedSweetDataScope
import com.deividasstr.ui.base.di.scopes.consumedsweethistory.ConsumedSweetHistoryScope
import com.deividasstr.ui.base.di.scopes.facts.FactsScope
import com.deividasstr.ui.base.di.scopes.sweetdetails.SweetDetailsScope
import com.deividasstr.ui.base.di.scopes.sweetsearchlist.SweetSearchListScope
import com.deividasstr.ui.features.addconsumedsweet.AddConsumedSweetFragment
import com.deividasstr.ui.features.consumedsweetdata.ConsumedSweetDataFragment
import com.deividasstr.ui.features.facts.FactsFragment
import com.deividasstr.ui.features.sweetdetails.SweetDetailsFragment
import com.deividasstr.ui.features.sweethistory.ConsumedSweetHistoryFragment
import com.deividasstr.ui.features.sweetsearchlist.SweetsSearchListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector()
    @FactsScope
    abstract fun factsFragment(): FactsFragment

    @ContributesAndroidInjector()
    @AddConsumedSweetScope
    abstract fun addConsumedSweetFragment(): AddConsumedSweetFragment

    @ContributesAndroidInjector()
    @ConsumedSweetHistoryScope
    abstract fun consumedSweetHistoryFragment(): ConsumedSweetHistoryFragment

    @ContributesAndroidInjector()
    @ConsumedSweetDataScope
    abstract fun consumedSweetDataFragment(): ConsumedSweetDataFragment

    @ContributesAndroidInjector()
    @SweetDetailsScope
    abstract fun sweetDetailsFragment(): SweetDetailsFragment

    @ContributesAndroidInjector()
    @SweetSearchListScope
    abstract fun sweetSearchListFragment(): SweetsSearchListFragment
}