package com.deividasstr.domain.usecases

import com.deividasstr.domain.models.ConsumedSweet
import com.deividasstr.domain.framework.CompletableUseCaseWithParameter
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.utils.OpenClass
import io.reactivex.Completable

@OpenClass
class AddConsumedSweetUseCase(private val repo: ConsumedSweetsRepo)
    : CompletableUseCaseWithParameter<ConsumedSweet> {

    override fun execute(parameter: ConsumedSweet): Completable {
        return repo.addSweet(parameter)
    }
}