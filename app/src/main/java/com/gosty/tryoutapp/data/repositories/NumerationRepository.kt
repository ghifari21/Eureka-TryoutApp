package com.gosty.tryoutapp.data.repositories

import androidx.lifecycle.LiveData
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.utils.Result

interface NumerationRepository {
    fun getAllNumerationTryouts(): LiveData<Result<List<SubjectModel>>>

    /*
    *   this is a method as an interface to get all numeration tryout data for explanation feature
    *   @author Andi
    *   @since September 8th, 2023
    * */
    fun getAllNumerationTryoutsForExplanation() : LiveData<Result<List<SubjectModel>>>

    fun postUserAnswer(answerModel: AnswerModel)

    fun getAllUserAnswer(): LiveData<Result<List<AnswerModel>>>

    /*
    *   this is a method as an interface to get user's data related to his/her scores for score feature by using firebase realtime db
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun getUserListScore() : LiveData<Result<List<ScoreModel>>>

    fun deleteAllUserAnswer()

    fun postUserScore(score: ScoreModel)
}