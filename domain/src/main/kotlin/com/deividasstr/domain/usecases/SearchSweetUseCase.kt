package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.framework.SingleUseCaseWithParameter
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Single

class SearchSweetUseCase(private val repo: SweetsRepo) : SingleUseCaseWithParameter<List<Sweet>, String> {

    override fun execute(name: String): Single<List<Sweet>> {
        return repo.search(name)
    }
}