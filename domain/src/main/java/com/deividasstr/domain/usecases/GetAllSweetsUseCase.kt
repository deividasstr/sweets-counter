package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.framework.SingleUseCase
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Single


class GetAllSweetsUseCase(private val repo: SweetsRepo) : SingleUseCase<List<Sweet>> {

    override fun execute(): Single<List<Sweet>> {
        return repo.getAllSweets()
    }

}