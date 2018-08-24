package com.deividasstr.domain.usecases

import com.deividasstr.domain.framework.SingleUseCase
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import io.reactivex.Single

class GetAllConsumedSweetsCalsUseCase(private val repo: ConsumedSweetsRepo) : SingleUseCase<Long> {

    override fun execute(): Single<Long> {
        return repo.getTotalCalsConsumed()
    }
}