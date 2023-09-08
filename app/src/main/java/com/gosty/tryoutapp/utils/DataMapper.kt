package com.gosty.tryoutapp.utils

import com.gosty.tryoutapp.data.models.DiscussionModel
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.models.SelectionAnswerModel
import com.gosty.tryoutapp.data.models.SelectionModel
import com.gosty.tryoutapp.data.models.ShortAnswerModel
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.data.remote.responses.DataItemResponse
import com.gosty.tryoutapp.data.remote.responses.DiscussionItemResponse
import com.gosty.tryoutapp.data.remote.responses.QuestionItemResponse
import com.gosty.tryoutapp.data.remote.responses.SelectionAnswerItemResponse
import com.gosty.tryoutapp.data.remote.responses.SelectionItemResponse
import com.gosty.tryoutapp.data.remote.responses.ShortAnswerItemResponse
import com.gosty.tryoutapp.data.remote.responses.TryoutItemResponse

object DataMapper {
    fun mapDataItemResponseToSubjectModel(input: DataItemResponse?): SubjectModel =
        SubjectModel(
            tryout = input?.tryout?.map { response ->
                mapTryoutItemResponseToTryoutModel(response)
            },
            subjectName = input?.subjectName,
            idSubject = input?.idSubject
        )

    fun mapTryoutItemResponseToTryoutModel(input: TryoutItemResponse?): TryoutModel =
        TryoutModel(
            subjectId = input?.subjectId,
            categoryName = input?.categoryName,
            categoryId = input?.categoryId,
            question = input?.question?.map { response ->
                mapQuestionItemResponseToQuestionModel(response)
            },
            subjectName = input?.subjectName,
            isFav = input?.isFav,
            id = input?.id
        )

    fun mapQuestionItemResponseToQuestionModel(input: QuestionItemResponse?): QuestionModel =
        QuestionModel(
            selection = input?.selection?.map { response ->
                mapSelectionItemResponseToSelectionModel(response)
            },
            selectionAnswer = input?.selectionAnswer?.map { response ->
                mapSelectionAnswerItemResponseToSelectionAnswerModel(response)
            },
            shortAnswer = input?.shortAnswer?.map { response ->
                mapShortAnswerItemResponseToShortAnswerModel(response)
            },
            essayAnswer = input?.essayAnswer,
            qtId = input?.qtId,
            questionText = input?.questionText,
            id = input?.id,
            discussion = input?.discussion?.map { response ->
                mapDiscussionItemResponseToDiscussionModel(response)
            },
            tryoutId = input?.tryoutId,
            questionId = input?.questionId,
            isEssay = input?.selection.isNullOrEmpty(),
            isMultipleAnswer = input?.selectionAnswer?.size!! > 1
        )

    fun mapSelectionItemResponseToSelectionModel(input: SelectionItemResponse?): SelectionModel =
        SelectionModel(
            image = input?.image,
            idSelection = input?.idSelection,
            selectionText = input?.selectionText,
            questionId = input?.questionId
        )

    fun mapSelectionAnswerItemResponseToSelectionAnswerModel(input: SelectionAnswerItemResponse?): SelectionAnswerModel =
        SelectionAnswerModel(
            image = input?.image,
            idAnswer = input?.idAnswer,
            selectionId = input?.selectionId,
            questionId = input?.questionId,
            selectionText = input?.selectionText
        )

    fun mapShortAnswerItemResponseToShortAnswerModel(input: ShortAnswerItemResponse?): ShortAnswerModel =
        ShortAnswerModel(
            secondRange = input?.secondRange,
            shortAnswerText = input?.shortAnswerText,
            firstRange = input?.firstRange,
            questionId = input?.questionId,
            idShortAnswer = input?.idShortAnswer
        )

    fun mapDiscussionItemResponseToDiscussionModel(input: DiscussionItemResponse?): DiscussionModel =
        DiscussionModel(
            image = input?.image,
            discussionText = input?.discussionText,
            questionId = input?.questionId,
            idDiscussion = input?.idDiscussion
        )
}