package com.deividasstr.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.deividasstr.data.utils.StrictModePermitter.permitDiskReads
import com.deividasstr.domain.entities.enums.MeasurementUnit
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.PrefsRepo
import javax.inject.Singleton

@Singleton
class SharedPrefs(private val ctx: Context) : PrefsRepo {

    companion object {
        const val PREFS_NAME = "PREFS"
        const val PREF_FACT_DATE = "PREF_FACT_DATE"
        const val PREF_SWEET_DATE = "PREF_SWEET_DATE"
        const val PREF_MEASUREMENT_UNIT = "PREF_MEASUREMENT_UNIT"
    }

    private val prefs: SharedPreferences by lazy {
        permitDiskReads {
            ctx.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
        }
    }

    var factsUpdatedDate: Long
        get() = permitDiskReads { prefs.getLong(PREF_FACT_DATE, 0) }
        set(value) {
            prefs.edit().putLong(PREF_FACT_DATE, value).apply()
        }

    var sweetsUpdatedDate: Long
        get() = permitDiskReads { prefs.getLong(PREF_SWEET_DATE, 0) }
        set(value) = prefs.edit().putLong(PREF_SWEET_DATE, value).apply()

    override suspend fun saveSweetsDownloadTime(): Either<Error, Either.None> {
        sweetsUpdatedDate = System.currentTimeMillis()
        return Either.Right(Either.None())
    }

    override suspend fun saveFactsDownloadTime(): Either<Error, Either.None> {
        factsUpdatedDate = System.currentTimeMillis()
        return Either.Right(Either.None())
    }

    var defaultMeasurementUnit: MeasurementUnit
        get() = permitDiskReads {
            val unit = prefs.getInt(PREF_MEASUREMENT_UNIT, MeasurementUnit.GRAM.ordinal)
            MeasurementUnit.values()[unit]
        }
        set(value) {
            prefs.edit().putInt(PREF_MEASUREMENT_UNIT, value.ordinal).apply()
        }
}
