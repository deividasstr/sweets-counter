package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.Sweet
import io.reactivex.Completable

interface NewSweetsRepo {

    fun newSweet(sweet: Sweet): Completable
}