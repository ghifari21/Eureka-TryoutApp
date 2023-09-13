package com.gosty.tryoutapp.ui.explanation

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.data.ui.RvExplanationAnswerAdapter
import com.gosty.tryoutapp.databinding.FragmentExplanationBinding

class ExplanationFragment : Fragment() {
    private var _binding : FragmentExplanationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExplanationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.getParcelable<QuestionModel>(EXTRA_DATA_EXPLANATION)
        val score = arguments?.getParcelable<ScoreModel>(EXTRA_DATA_SCORE)

        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY

        binding.mvQuestionExplanation.text = Html.fromHtml(question?.questionText, flags, { source ->
            Glide.with(requireActivity())
                .load(source.replace(""""""", ""))
                .override(1000,700)
                .into(binding.ivQuestionExplanation)
            null
        }, null).toString()

        if (question?.tryoutId == 30) {
            binding.tvAnswerEssay.isVisible = false
            binding.rvAnswer.isVisible = true
            binding.lblJawabanYangBenar.isVisible = false
            binding.tvCorrectAnswer.isVisible = false
            binding.rvAnswer.isEnabled = true

            binding.rvAnswer.apply {
                adapter = RvExplanationAnswerAdapter(question.selectionAnswer,question.selection!!, score, question.selection.size, requireActivity())
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
            }
            for (i in score?.answers!!){
                for (j in question.selectionAnswer!!){
                    if (i.questionId == j?.questionId){
                        if (i.answer?.isEmpty() == true){
                            binding.tvNotAnswerInfo.isVisible = true
                        } else {
                            binding.tvNotAnswerInfo.isVisible = false
                        }
                    }
                }
            }
        } else {
            if (question?.selection?.isEmpty() == true){
                binding.tvAnswerEssay.isVisible = true
                binding.rvAnswer.isEnabled = false
                binding.lblJawabanYangBenar.isVisible = true
                binding.tvCorrectAnswer.isVisible = true

                for (i in score?.answers!!){
                    if (i.questionId == question.questionId){
                        for (j in i.answer!!){
                            binding.tvAnswerEssay.text = j
                        }
                    }
                }
                if (binding.tvAnswerEssay.text.isEmpty()){
                    binding.tvNotAnswerInfo.isVisible = true
                } else {
                    binding.tvNotAnswerInfo.isVisible = false
                }
                binding.tvCorrectAnswer.text = question?.shortAnswer?.get(0)?.shortAnswerText
            } else {
                binding.rvAnswer.isEnabled = true
                binding.tvAnswerEssay.isVisible = false
                binding.lblJawabanYangBenar.isVisible = false
                binding.tvCorrectAnswer.isVisible = false

                binding.rvAnswer.apply {
                    adapter = RvExplanationAnswerAdapter(question?.selectionAnswer,question?.selection!!, score, question.selection.size, requireActivity())
                    layoutManager = LinearLayoutManager(activity)
                    setHasFixedSize(true)
                }
                for (i in score?.answers!!){
                    for (j in question?.selectionAnswer!!){
                        if (i.questionId == j?.questionId){
                            if (i.answer?.isEmpty() == true){
                                binding.tvNotAnswerInfo.isVisible = true
                            } else {
                                binding.tvNotAnswerInfo.isVisible = false
                            }
                        }
                    }
                }
            }
        }

        binding.mvExplanation.text = Html.fromHtml(question?.discussion?.get(0)?.discussionText, flags, { source ->
            Glide.with(requireActivity())
                .load(source.replace(""""""", ""))
                .override(1000,700)
                .into(binding.ivExplanation)
            null
        }, null).toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA_EXPLANATION = "extra_explanation"
        const val EXTRA_DATA_SCORE = "extra_score"
    }
}