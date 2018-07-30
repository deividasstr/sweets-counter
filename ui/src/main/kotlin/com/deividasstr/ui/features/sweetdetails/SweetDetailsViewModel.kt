package com.deividasstr.ui.features.sweetdetails

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.entities.ConsumedSweet
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
import kotlin.math.roundToInt

class SweetDetailsViewModel
@Inject constructor(
    private val getSweetByIdUseCase: GetSweetByIdUseCase,
    private val addConsumedSweetUseCase: AddConsumedSweetUseCase,
    private val dateTimeHandler: DateTimeHandler) : BaseViewModel() {

    val sweet = MutableLiveData<SweetUi>()
    val sweetRating = MediatorLiveData<Int>().apply {
        value = R.color.white
        addSource(sweet) {
            it?.let { postValue(rating(it)) }
        }
    }

    var measureUnitServing = true

    val enteredValue = MutableLiveData<String>().apply {
        value = "0"
    }

    val totalCals = MediatorLiveData<BigDecimal>().apply {
        value = BigDecimal.ZERO
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
            setError(StringResException(R.string.add_sweet_validation_fail))
        }
    }

    private fun getConsumedSweet(): ConsumedSweet {
        val units: Int = if (measureUnitServing) {
            (enteredValue.value!!.toInt() * sweet.value!!.servingG).roundToInt()
        } else {
            enteredValue.value!!.toInt()
        }

        return ConsumedSweet(
            sweetId = sweet.value!!.id,
            g = units,
            date = dateTimeHandler.currentDateTimeMillis())
    }

    private fun validateConsumedSweet(): Boolean {
        return enteredValue.value!!.isNotEmpty() && enteredValue.value!!.toInt() > 0
    }

    fun toggleMeasureUnit() {
        measureUnitServing = !measureUnitServing
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
            sweet.sweetRating() == SweetRating.BAD -> R.color.rating_bad
            sweet.sweetRating() == SweetRating.AVERAGE -> R.color.rating_average
            else -> R.color.rating_good
        }
    }

    private fun getTotalCals(): BigDecimal {
        val sweet = sweet.value
        if (enteredValue.value.isNullOrEmpty() || sweet == null) {
            return BigDecimal.ZERO
        }
        val enteredValue = enteredValue.value!!.toBigDecimal()
        return when {
            measureUnitServing -> enteredValue
                .multiply(BigDecimal(sweet.calsPer100))
                .multiply(BigDecimal(sweet.servingG))
                .divide(BigDecimal(100))
            else -> enteredValue.multiply(BigDecimal(sweet.calsPer100)).divide(BigDecimal(100))
        }
    }
}