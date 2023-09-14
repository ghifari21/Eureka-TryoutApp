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
    private val _sending = MediatorLiveData<Result<String>>()
    val sending: LiveData<Result<String>> get() = _sending

    /***
     * This method to get all user answers from realtime database.
     * @author Ghifari Octaverin
     * @since Sept 13th, 2023
     */
    fun getAllUserAnswer() {
        val result = numerationRepository.getAllUserAnswer()
        _answers.addSource(result) {
            _answers.postValue(it)
        }
    }

    /***
     * This method to send user score to realtime database.
     * @param score variable that contain score user
     * @author Ghifari Octaverin
     * @since Sept 11th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun postScore(score: ScoreModel) {
        val data = numerationRepository.postUserScore(score)
        _sending.addSource(data) {
            _sending.postValue(it)
        }
    }
}