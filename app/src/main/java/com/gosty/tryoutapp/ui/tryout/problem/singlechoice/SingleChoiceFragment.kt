package com.gosty.tryoutapp.ui.tryout.problem.singlechoice

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.models.SelectionModel
import com.gosty.tryoutapp.data.ui.RvListMultipleChoiceAdapter
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.FragmentSingleChoiceBinding
import com.gosty.tryoutapp.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleChoiceFragment : Fragment() {
    private var _binding: FragmentSingleChoiceBinding? = null
    private val binding get() = _binding
    private val viewModel: SingleChoiceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChoiceBinding.inflate(inflater, container, false)
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

        val adapter = RvListMultipleChoiceAdapter()
        val layoutManager = LinearLayoutManager(activity)
        binding?.rvAnswerSection?.adapter = adapter
        binding?.rvAnswerSection?.layoutManager = layoutManager
        binding?.rvAnswerSection?.setHasFixedSize(true)

        adapter.submitList(question?.selection!!)

        adapter.setOnItemClickCallback(object : RvListMultipleChoiceAdapter.OnItemClickCallback {
            override fun onItemClicked(selection: SelectionModel, position: Int) {
                selection.isAnswered = !selection.isAnswered
                val answers = mutableListOf<String>()
                for (i in question.selection.indices) {
                    if (question.selection[i]?.idSelection != selection.idSelection && question.selection[i]?.isAnswered == true) {
                        question.selection[i]?.isAnswered = false
                    }
                    if (question.selection[i]?.isAnswered == true) {
                        answers.add(question.selection[i]?.selectionText.toString())
                    }
                    adapter.notifyItemChanged(i)
                }
                val answer = AnswerModel(
                    id = Utility.getRandomString().toInt(),
                    tryoutId = question.tryoutId,
                    questionId = question.questionId,
                    essay = false,
                    answer = answers,
                    correct = answers[0] == question.selectionAnswer?.get(0)!!.selectionText
                )
                viewModel.postAnswer(answer)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}