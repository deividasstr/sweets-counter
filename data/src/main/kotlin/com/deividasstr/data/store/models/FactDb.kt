package com.deividasstr.data.store.models

import com.deividasstr.data.networking.models.ResponseFact
import com.deividasstr.domain.models.Fact
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class FactDb(@Id(assignable = true) var id: Long, val text: String) {
    constructor(fact: Fact) : this(fact.id, fact.text)
    constructor(fact: ResponseFact) : this(fact.id, fact.fact)
}

fun FactDb.toFact(): Fact {
    return Fact(id, text)
}
