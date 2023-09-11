package com.gosty.tryoutapp.ui.profile

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {

    /*
    *   this method is to get user's score data for profile feature
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun getScoreData() = numerationRepository.getUserListScore()
}