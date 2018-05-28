package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Fact
import com.deividasstr.domain.framework.SingleUseCaseWithParameter
import com.deividasstr.domain.repositories.FactRepo
import io.reactivex.Single

class GetRandomFactUseCase(private val repo: FactRepo) : SingleUseCaseWithParameter<Fact, Long> {

    override fun execute(currentId: Long): Single<Fact> {
        return repo.getRandomFact(currentId)
    }
}