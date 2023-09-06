package com.gosty.tryoutapp.ui.tryout.problem

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    fun getSubjects() = numerationRepository.getAllNumerationTryouts()
}