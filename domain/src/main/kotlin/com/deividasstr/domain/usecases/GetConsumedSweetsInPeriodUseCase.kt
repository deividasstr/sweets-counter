package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.framework.SingleUseCaseWithParameter
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.utils.DateRange
import io.reactivex.Single

class GetConsumedSweetsInPeriodUseCase(private val repo: ConsumedSweetsRepo)
    : SingleUseCaseWithParameter<List<ConsumedSweet>, DateRange> {

    override fun execute(dateRange: DateRange): Single<List<ConsumedSweet>> {
        return repo.getConsumedSweetsByPeriod(dateRange)
    }
}