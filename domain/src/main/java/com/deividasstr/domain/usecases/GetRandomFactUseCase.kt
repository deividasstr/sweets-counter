package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Fact
import com.deividasstr.domain.framework.SingleUseCaseWithParameter
import com.deividasstr.domain.repositories.FactRepo
import io.reactivex.Single

class GetRandomFactUseCase(private val repo: FactRepo) : SingleUseCaseWithParameter<Fact, Int> {

    override fun execute(currentId: Int): Single<Fact> {
        return repo.getRandomFact(currentId)
    }
}