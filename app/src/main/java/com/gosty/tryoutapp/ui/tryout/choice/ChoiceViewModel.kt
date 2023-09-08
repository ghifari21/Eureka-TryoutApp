package com.gosty.tryoutapp.ui.tryout.choice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import com.gosty.tryoutapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChoiceViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    private val _tryouts = MediatorLiveData<Result<List<SubjectModel>>>()
    val tryouts: LiveData<Result<List<SubjectModel>>> get() = _tryouts

    fun getSubjects() {
        val result = numerationRepository.getAllNumerationTryouts()
        _tryouts.addSource(result) {
            _tryouts.postValue(it)
        }
    }
}