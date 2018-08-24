package com.deividasstr.ui.features.sweetdetails

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.enums.MeasurementUnit
import com.deividasstr.domain.enums.toggle
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.models.SweetUi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import javax.inject.Inject

class SweetDetailsViewModel
@Inject constructor(
    private val getSweetByIdUseCase: GetSweetByIdUseCase,
    private val addConsumedSweetUseCase: AddConsumedSweetUseCase,
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    val sweet = MutableLiveData<SweetUi>()
    val sweetRating = MediatorLiveData<Int>().apply {
        value = R.color.white
        addSource(sweet) { it ->
            it?.let { postValue(rating(it)) }
        }
    }

    private var measureUnit = defaultMeasureUnit()

    private fun defaultMeasureUnit(): MeasurementUnit {
        return sharedPrefs.defaultMeasurementUnit
    }

    fun measureUnitGrams(): Boolean {
        return measureUnit == MeasurementUnit.GRAM
    }

    val enteredValue = MutableLiveData<String>().apply {
        value = "0"
    }

    val totalCals = MediatorLiveData<Long>().apply {
        value = 0
        addSource(enteredValue) {
            postValue(getTotalCals())
        }
    }

    var sweetId: Long = 0
        set(value) {
            field = value
            getSweet()
        }

    fun validate(navigationCallback: NavigationCallback) {
        if (validateConsumedSweet()) {
            if (realisticAmount()) {
                val consumedSweet = getConsumedSweet()
                addConsumedSweetUseCase.execute(consumedSweet)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        navigationCallback.onNavigate()
                    }, onError = {
                        setError(it as StringResException)
                    })
            } else {
                setError(StringResException(R.string.add_sweet_validation_too_much))
            }
        } else {
            setError(StringResException(R.string.add_sweet_validation_fail))
        }
    }

    // Amount > 10kg is not realistic, c'mon
    private fun realisticAmount(): Boolean {
        return enteredValue.value!!.toLong() < 10 * 1000
    }

    private fun getConsumedSweet(): ConsumedSweet {
        val amount = enteredValue.value!!.toLong() * measureUnit.ratioWithGrams
        println("am $amount val ${enteredValue.value} meas rat ${measureUnit.ratioWithGrams}")
        return ConsumedSweet(
            sweetId = sweet.value!!.id,
            g = amount,
            date = dateTimeHandler.currentEpochSecs())
    }

    private fun validateConsumedSweet(): Boolean {
        return enteredValue.value!!.isNotEmpty() && enteredValue.value!!.toLong() > 0
    }

    fun toggleMeasureUnit() {
        measureUnit = measureUnit.toggle()
        sharedPrefs.defaultMeasurementUnit = measureUnit
        totalCals.value = getTotalCals()
    }

    private fun getSweet() {
        val disposable = getSweetByIdUseCase.execute(sweetId)
            .subscribeOn(Schedulers.io())
            .map { SweetUi(it) }
            .subscribeBy(onSuccess = {
                sweet.postValue(it)
            },
                onError = {
                    setError(it as StringResException)
                }
            )
        addDisposable(disposable)
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
            .divide(BigDecimal(100)).toLong()
    }
}