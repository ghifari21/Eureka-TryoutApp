package com.gosty.tryoutapp.ui.score

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    /*
    *   this method is to get user's data related to his/her scores
    * */
    fun getListScore() = numerationRepository.getUserListScore()
}