package com.gosty.tryoutapp.ui.tryout.problem.singlechoice

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.models.SelectionModel
import com.gosty.tryoutapp.data.ui.RvListMultipleChoiceAdapter
import com.gosty.tryoutapp.databinding.FragmentSingleChoiceBinding

class SingleChoiceFragment : Fragment() {
    private var _binding: FragmentSingleChoiceBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChoiceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.getParcelable<QuestionModel>(EXTRA_DATA)

        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY
        binding?.mvQuestion?.text = Html.fromHtml(question?.questionText, flags, { source ->
            Glide.with(requireActivity())
                .load(source.replace(""""""", ""))
                .into(binding?.ivQuestionImage!!)

            null
        }, null).toString()

        val adapter = RvListMultipleChoiceAdapter()
        val layoutManager = LinearLayoutManager(activity)
        binding?.rvAnswerSection?.adapter = adapter
        binding?.rvAnswerSection?.layoutManager = layoutManager
        binding?.rvAnswerSection?.setHasFixedSize(true)

        Log.i("SELECTION", question.toString())

        adapter.submitList(question?.selection!!)

        adapter.setOnItemClickCallback(object : RvListMultipleChoiceAdapter.OnItemClickCallback {
            override fun onItemClicked(selection: SelectionModel) {
                selection.isAnswered = !selection.isAnswered
                for (i in question.selection.indices) {
                    if (question.selection[i]?.idSelection != selection.idSelection && question.selection[i]?.isAnswered == true) {
                        question.selection[i]?.isAnswered = false
                    }
                    adapter.notifyItemChanged(i)
                }
                Log.i("SELECTION 2", question.selection.toString())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "extra"
    }
}