package com.gosty.tryoutapp.data.repositories

import androidx.lifecycle.LiveData
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.utils.Result

interface NumerationRepository {
    /***
     * This method is a contract to implement in NumerationRepositoryImpl to get all the numeration tryouts.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     */
    fun getAllNumerationTryouts(): LiveData<Result<List<SubjectModel>>>

    /***
     *   this is a method as an interface to get all numeration tryout data for explanation feature
     *   @author Andi
     *   @since September 8th, 2023
     */
    fun getAllNumerationTryoutsForExplanation() : LiveData<Result<List<SubjectModel>>>

    /***
     * This method is a contract to implement in NumerationRepositoryImpl to send user answer to realtime database.
     * @param answerModel variable that contain user answer
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun postUserAnswer(answerModel: AnswerModel): LiveData<Result<String>>

    /***
     * This method is a contract to implement in NumerationRepositoryImpl to get all user answers from realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     */
    fun getAllUserAnswer(): LiveData<Result<List<AnswerModel>>>

    /***
     *   this is a method as an interface to get user's data related to his/her scores for score feature by using firebase realtime db
     *   @author Andi
     *   @since September 11th, 2023
     */
    fun getUserListScore() : LiveData<Result<List<ScoreModel>>>

    /***
     * This method is a contract to implement in NumerationRepositoryImpl to delete all user answers in realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun deleteAllUserAnswer(): LiveData<Result<String>>

    /***
     * This method is a contract to implement in NumerationRepositoryImpl to send user score to realtime database.
     * @param score variable that contain user score model
     * @author Ghifari Octaverin
     * @since Sept 11th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun postUserScore(score: ScoreModel): LiveData<Result<String>>
}