package com.deividasstr.domain.utils

import org.threeten.bp.LocalDate

data class DateRange(
    override val start: LocalDate,
    override val endInclusive: LocalDate
) : ClosedRange<LocalDate>, Iterable<LocalDate> {

    override fun iterator(): Iterator<LocalDate> = DateIterator(this)
    override fun contains(value: LocalDate): Boolean = value in start..endInclusive
}

class DateIterator(val date: DateRange) : Iterator<LocalDate> {
    private var current = date.start
    override fun hasNext(): Boolean = current <= date.endInclusive

    override fun next(): LocalDate {
        val result = current
        current = current.plusDays(1)
        return result
    }
}