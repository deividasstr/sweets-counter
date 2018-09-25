package com.deividasstr.domain.usecases

import com.deividasstr.domain.framework.SingleUseCase
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.utils.OpenClass
import io.reactivex.Single

@OpenClass
class GetAllConsumedSweetsUseCase(private val repo: ConsumedSweetsRepo) :
    SingleUseCase<List<ConsumedSweet>> {

    override fun execute(): Single<List<ConsumedSweet>> {
        return repo.getAllConsumedSweets()
    }
}