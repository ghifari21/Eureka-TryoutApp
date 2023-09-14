package com.gosty.tryoutapp.ui.explanation

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExplanationViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {

    /***
    *   this method is to get numeration tryout data for explanation feature
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun getSubjectForExplanation() = numerationRepository.getAllNumerationTryoutsForExplanation()
}