package com.gosty.tryoutapp.ui.tryout_done

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TryoutDoneViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    fun getAllUserAnswer() = numerationRepository.getAllUserAnswer()

    fun getAllTryout() = numerationRepository.getAllNumerationTryouts()
}