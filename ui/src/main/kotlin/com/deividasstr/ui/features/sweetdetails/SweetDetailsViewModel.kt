package com.deividasstr.ui.features.sweetdetails

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.MeasurementUnit
import com.deividasstr.domain.entities.enums.toggle
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.base.models.toSweet
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class SweetDetailsViewModel
@Inject constructor(
    private val addConsumedSweetUseCase: AddConsumedSweetUseCase,
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    val sweet = MutableLiveData<SweetUi>()
    val sweetRating = MediatorLiveData<Int>()

    private var measureUnit = sharedPrefs.defaultMeasurementUnit

    val enteredValue = MutableLiveData<String>()

    private var navigationCallback: NavigationCallback? = null

    val totalCals = MediatorLiveData<Long>().apply {
        value = 0
        addSource(enteredValue) { postValue(getTotalCals()) }
    }

    fun validate(callback: NavigationCallback) {
        navigationCallback = callback
        if (validateConsumedSweet()) {
            val consumedSweet = getConsumedSweet()
            scope.launch {
                addConsumedSweetUseCase(consumedSweet) { it.either(::handleError, ::handleSuccess) }
            }
        } else {
            setError(Error(R.string.add_sweet_validation_fail))
        }
    }

    private fun handleSuccess() {
        navigationCallback?.onNavigate()
        navigationCallback = null
    }

    private fun handleError(error: Error) {
        setError(error)
    }

    fun measureUnitGrams(): Boolean {
        return measureUnit == MeasurementUnit.GRAM
    }

    fun setSweet(sweet: SweetUi) {
        this.sweet.value = sweet
        sweetRating.value = rating(sweet)
    }

    fun restore(enteredVal: String) {
        enteredValue.postValue(enteredVal)
    }

    fun toggleMeasureUnit() {
        measureUnit = measureUnit.toggle()
        sharedPrefs.defaultMeasurementUnit = measureUnit
        totalCals.value = getTotalCals()
    }

    private fun getConsumedSweet(): ConsumedSweet {
        val amount = enteredValue.value!!.toLong() * measureUnit.ratioWithGrams
        val sweetUi = sweet.value!!
        return ConsumedSweet(
            sweetId = sweetUi.id,
            g = amount,
            date = dateTimeHandler.currentEpochSecs(),
            sweet = sweetUi.toSweet()
        )
    }

    private fun validateConsumedSweet(): Boolean {
        return enteredValue.value?.let {
            it.isNotEmpty() && it.toLong() > 0
        } ?: false
    }

    private fun rating(sweet: SweetUi): Int {
        return when {
            sweet.sweetRating() == SweetRating.BAD -> R.drawable.rating_bad
            sweet.sweetRating() == SweetRating.AVERAGE -> R.drawable.rating_average
            else -> R.drawable.rating_good
        }
    }

    private fun getTotalCals(): Long {
        val sweet = sweet.value
        if (enteredValue.value.isNullOrEmpty() || sweet == null) {
            return 0
        }
        val enteredValue = enteredValue.value!!.toBigDecimal()
        return enteredValue
            .multiply(BigDecimal(measureUnit.ratioWithGrams))
            .multiply(BigDecimal(sweet.calsPer100))
            .divide(BigDecimal(100))
            .toLong()
    }
}