package com.gosty.tryoutapp.ui.explanation

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExplanationViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    fun getSubjectForExplanation() = numerationRepository.getAllNumerationTryoutsForExplanation()
}