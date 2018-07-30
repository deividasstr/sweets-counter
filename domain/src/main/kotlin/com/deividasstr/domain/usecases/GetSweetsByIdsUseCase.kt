package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.framework.SingleUseCaseWithParameter
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.domain.utils.OpenClass
import io.reactivex.Single

@OpenClass
class GetSweetsByIdsUseCase(private val repo: SweetsRepo) :
    SingleUseCaseWithParameter<List<Sweet>, LongArray> {

    override fun execute(parameter: LongArray): Single<List<Sweet>> {
        return repo.getSweetsByIds(parameter)
    }
}