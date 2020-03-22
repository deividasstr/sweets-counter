package com.deividasstr.ui.base.models

import com.deividasstr.domain.entities.models.Fact

data class FactUi(val id: Long, val text: String) {
    constructor(fact: Fact) : this (fact.id, fact.text)
}
