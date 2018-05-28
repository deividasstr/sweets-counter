package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.framework.SingleUseCaseWithParameter
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Single

class GetSweetByIdUseCase(private val repo: SweetsRepo) : SingleUseCaseWithParameter<Sweet, Long> {

    override fun execute(id: Long): Single<Sweet> {
        return repo.getSweetById(id)
    }
}