package com.gosty.tryoutapp.ui.tryout.problem.essay

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.FragmentEssayBinding
import com.gosty.tryoutapp.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EssayFragment : Fragment() {
    private var _binding: FragmentEssayBinding? = null
    private val binding get() = _binding
    private val viewModel: EssayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEssayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.getParcelable<QuestionModel>(TabPagerProblemAdapter.EXTRA_DATA)
        val pos = arguments?.getString(TabPagerProblemAdapter.EXTRA_POS)
        val total = arguments?.getString(TabPagerProblemAdapter.EXTRA_TOTAL)

        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY
        binding?.mvQuestion?.text = Html.fromHtml(question?.questionText, flags, { source ->
            Glide.with(requireActivity())
                .load(source.replace(""""""", ""))
                .into(binding?.ivQuestionImage!!)

            null
        }, null).toString()

        binding?.tvTotalProblem?.text = activity?.getString(R.string.number_of_problem, pos, total)

        binding?.etEssay?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val essay = binding?.etEssay?.text.toString().trim().replace(".", ",")
                val shortAnswerFirstRange =
                    question?.shortAnswer!![0]!!.firstRange
                val shortAnswerSecondRange =
                    question.shortAnswer[0]!!.secondRange
                val shortAnswer =
                    question.shortAnswer[0]!!.shortAnswerText

                val firstRange = if (shortAnswerFirstRange!! > shortAnswerSecondRange!!) {
                    shortAnswerFirstRange
                } else {
                    shortAnswerSecondRange
                }

                val secondRange = if (shortAnswerFirstRange < shortAnswerSecondRange) {
                    shortAnswerFirstRange
                } else {
                    shortAnswerSecondRange
                }

                Log.i("RANGE", "${firstRange..secondRange}")

                val answer = AnswerModel(
                    id = Utility.getRandomString().toInt(),
                    tryoutId = question.tryoutId,
                    questionId = question.questionId,
                    essay = true,
                    answer = listOf(essay),
                    correct = (essay in secondRange..firstRange) || essay == shortAnswer
                )
                viewModel.postAnswer(answer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}