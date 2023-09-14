package com.gosty.tryoutapp.ui.tryout.problem.singlechoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import com.gosty.tryoutapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleChoiceViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    private val _result = MediatorLiveData<Result<String>>()
    val result: LiveData<Result<String>> get() = _result

    /***
     * This method to send user answer to realtime database.
     * @param answerModel contain user answer
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun postAnswer(answerModel: AnswerModel) {
        val data = numerationRepository.postUserAnswer(answerModel)
        _result.addSource(data) {
            _result.postValue(it)
        }
    }
}