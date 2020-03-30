package com.deividasstr.data.utils

import kotlin.random.Random

class Randomiser {

    private val randomGenerator: Random by lazy { Random.Default }

    fun randomInt(lower: Int, upper: Int): Int {
        return randomGenerator.nextInt(lower, upper)
    }
}