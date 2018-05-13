package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.Fact
import io.reactivex.Single

interface FactRepo {

    fun getRandomFact(currentFactId: Int) : Single<Fact>
}