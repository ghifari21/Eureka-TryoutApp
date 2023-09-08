package com.gosty.tryoutapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.data.remote.network.ApiService
import com.gosty.tryoutapp.utils.DataMapper
import com.gosty.tryoutapp.utils.Result
import javax.inject.Inject

class NumerationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val crashlytics: FirebaseCrashlytics
) : NumerationRepository {
    override fun getAllNumerationTryouts(): LiveData<Result<List<SubjectModel>>> = liveData {
        emit(Result.Loading)
        val subjectList = MutableLiveData<List<SubjectModel>>()
        try {
            val response = apiService.getAllNumerationTryouts()
            if (response != null) {
                subjectList.value = response.data?.map {
                    DataMapper.mapDataItemResponseToSubjectModel(it)
                }
            }
        } catch (e: Exception) {
            crashlytics.log(e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val data: LiveData<Result<List<SubjectModel>>> = subjectList.map {
            Result.Success(it)
        }
        emitSource(data)
    }

    override fun getAllNumerationTryoutsForExplanation(): LiveData<Result<List<SubjectModel>>> = liveData {
        emit(Result.Loading)
        val subjectList = MutableLiveData<List<SubjectModel>>()
        try {
            val response = apiService.getAllNumerationTryouts()
            if (response != null) {
                subjectList.value = response.data?.map {
                    DataMapper.mapDataItemResponseToSubjectModel(it)
                }
            }
        } catch (e: Exception) {
            crashlytics.log(e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val data: LiveData<Result<List<SubjectModel>>> = subjectList.map {
            Result.Success(it)
        }
        emitSource(data)
    }
}