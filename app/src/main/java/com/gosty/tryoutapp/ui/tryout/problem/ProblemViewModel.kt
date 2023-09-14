package com.gosty.tryoutapp.ui.tryout.problem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import com.gosty.tryoutapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    private val _result = MediatorLiveData<Result<String>>()
    val result: LiveData<Result<String>> get() = _result

    /***
     * This method to delete all user answer in realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun deleteAllUserAnswer() {
        val data = numerationRepository.deleteAllUserAnswer()
        _result.addSource(data) {
            _result.postValue(it)
        }
    }
}