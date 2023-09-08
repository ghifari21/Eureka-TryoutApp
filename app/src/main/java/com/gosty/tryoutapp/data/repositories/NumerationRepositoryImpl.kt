package com.gosty.tryoutapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gosty.tryoutapp.BuildConfig
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.data.remote.network.ApiService
import com.gosty.tryoutapp.utils.DataMapper
import com.gosty.tryoutapp.utils.Result
import javax.inject.Inject

class NumerationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val db: FirebaseDatabase,
    private val auth: FirebaseAuth,
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

    override fun postUserAnswer(answerModel: AnswerModel) {
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.ANSWER_REF)
        ref.child(userId!!).child(answerModel.questionId.toString()).setValue(answerModel)
            .addOnFailureListener {
                Log.e("POST ANSWER", it.message.toString())
                crashlytics.log(it.message.toString())
            }
    }

    override fun getAllUserAnswer(): LiveData<Result<List<AnswerModel>>> {
        val result = MutableLiveData<Result<List<AnswerModel>>>()
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.ANSWER_REF)

        result.value = Result.Loading
        ref.child(userId!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.map {
                    it.getValue(AnswerModel::class.java)!!
                }
                result.value = Result.Success(data)
            }

            override fun onCancelled(error: DatabaseError) {
                result.value = Result.Error(error.message)
                crashlytics.log(error.message)
            }
        })

        return result
    }

    override fun deleteAllUserAnswer() {
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.ANSWER_REF)
        ref.child(userId!!).removeValue()
            .addOnFailureListener {
                Log.e("DELETE ALL ANSWER", it.message.toString())
                crashlytics.log(it.message.toString())
            }
    }
}