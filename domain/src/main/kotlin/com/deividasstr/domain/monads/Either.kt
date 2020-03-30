/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deividasstr.domain.monads

import com.deividasstr.domain.monads.Either.Left
import com.deividasstr.domain.monads.Either.Right
import org.jetbrains.annotations.TestOnly

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {

    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val left: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val right: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    fun either(fnL: (L) -> Any, fnR: () -> Any): Any =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR()
        }

    fun <N> map(map: (R) -> N): Either<L, N> {
        return when (this) {
            is Right -> Right(map(right))
            is Left -> this
        }
    }

    @TestOnly
    fun getValue(): Any? {
        return when (this) {
            is Right -> this.right
            is Left -> this.left
        }
    }

    class None {
        override fun equals(other: Any?): Boolean {
            return other is None
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
}
