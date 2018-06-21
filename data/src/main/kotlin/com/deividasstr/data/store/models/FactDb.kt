package com.deividasstr.data.store.models

import com.deividasstr.data.networking.models.ResponseFact
import com.deividasstr.domain.entities.Fact
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class FactDb (@Id(assignable = true) var id: Long, val text: String, val addedTimestamp: Long) {
    constructor(fact: Fact) : this(fact.id, fact.text, 0)
    constructor(fact: ResponseFact) : this(fact.id, fact.fact, fact.timestamp)
}

fun FactDb.toFact(): Fact {
    return Fact(id, text)
}

