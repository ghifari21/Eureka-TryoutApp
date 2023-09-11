package com.gosty.tryoutapp.ui.tryout_done

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import com.gosty.tryoutapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TryoutDoneViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    private val _answers = MediatorLiveData<Result<List<AnswerModel>>>()
    val answers: LiveData<Result<List<AnswerModel>>> get() = _answers

    fun getAllUserAnswer() {
        val result = numerationRepository.getAllUserAnswer()
        _answers.addSource(result) {
            _answers.postValue(it)
        }
    }

    fun postScore(score: ScoreModel) = numerationRepository.postUserScore(score)
}