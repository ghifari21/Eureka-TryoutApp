package com.gosty.tryoutapp.ui.tryout.problem.multiplechoice

import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.FragmentMultipleChoiceBinding
import com.gosty.tryoutapp.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import io.github.kexanie.library.MathView

@AndroidEntryPoint
class MultipleChoiceFragment : Fragment() {
    private var _binding: FragmentMultipleChoiceBinding? = null
    private val binding get() = _binding
    private val viewModel: MultipleChoiceViewModel by viewModels()
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMultipleChoiceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.getParcelable<QuestionModel>(TabPagerProblemAdapter.EXTRA_DATA)
        val pos = arguments?.getString(TabPagerProblemAdapter.EXTRA_POS)
        val total = arguments?.getString(TabPagerProblemAdapter.EXTRA_TOTAL)
        val currentItem = arguments?.getInt(TabPagerProblemAdapter.CURRENT_ITEM)

        tabLayout = requireActivity().findViewById(R.id.tab_layout)

        val imageList = mutableListOf<String>()
        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY
        binding?.mvQuestion?.text = Html.fromHtml(question?.questionText, flags, { source ->
            imageList.add(source.replace("""\""", ""))
            null
        }, null).toString()

        imageHandler(imageList)

        answerHandler(question, currentItem)

        binding?.tvTotalProblem?.text = activity?.getString(R.string.number_of_problem, pos, total)
    }

    private fun imageHandler(imageList: List<String>) {
        for (i in imageList) {
            val imageView = ImageView(requireActivity())

            Glide.with(requireActivity())
                .load(i)
                .placeholder(R.drawable.icon_black_image_placeholder)
                .error(R.drawable.icon_black_broken_image)
                .into(imageView)

            val width = 720
            val height = 480
            val layoutParams = LinearLayout.LayoutParams(width, height)
            layoutParams.gravity = Gravity.CENTER
            imageView.layoutParams = layoutParams
            binding?.imageContainer?.addView(imageView)
        }
    }

    private fun answerHandler(question: QuestionModel?, currentItem: Int?) {
        for (i in question?.selection!!) {
            val linearLayout = LinearLayout(requireActivity())
            val linearLayoutParams =
                ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                )

            linearLayoutParams.setMargins(0, 0, 0, 16)
            linearLayout.layoutParams = linearLayoutParams

            linearLayout.setPadding(16)
            linearLayout.elevation = 2f
            linearLayout.setBackgroundResource(R.drawable.shape_bg_rounded_corner_multiple_choice_gray_20_full_radius)

            val imageView = ImageView(requireActivity())
            imageView.setBackgroundResource(R.drawable.icon_white_check_circle)
            val ivParams =
                LinearLayout.LayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
            ivParams.gravity = Gravity.CENTER_VERTICAL
            ivParams.setMargins(0, 0, 16, 0)
            imageView.layoutParams = ivParams
            linearLayout.addView(imageView)

            if (i?.selectionText != "") {
                val mathView = MathView(requireActivity(), null)
                val mvLayoutParams = LinearLayout.LayoutParams(
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                )
                mathView.text = i?.selectionText
                mvLayoutParams.gravity = Gravity.CENTER
                mathView.layoutParams = mvLayoutParams
                mathView.setEngine(MathView.Engine.MATHJAX)
                linearLayout.addView(mathView)
            } else {
                val imageViewChoice = ImageView(requireActivity())

                Glide.with(requireActivity())
                    .load(i.image)
                    .placeholder(R.drawable.icon_black_image_placeholder)
                    .error(R.drawable.icon_black_broken_image)
                    .into(imageViewChoice)

                val width = 720
                val height = 480
                val ivChoiceParams = LinearLayout.LayoutParams(width, height)
                ivChoiceParams.gravity = Gravity.CENTER
                imageViewChoice.layoutParams = ivChoiceParams
                linearLayout.addView(imageViewChoice)
            }

            linearLayout.setOnClickListener {
                val answerList = mutableListOf<String>()
                i?.isAnswered = !i?.isAnswered!!
                for (j in 0..<binding?.multipleChoiceContainer?.childCount!!) {
                    val child = binding?.multipleChoiceContainer?.getChildAt(j)
                    if (child is LinearLayout) {
                        if (question.selection[j]?.isAnswered == false) {
                            child.setBackgroundResource(R.drawable.shape_bg_rounded_corner_multiple_choice_gray_20_full_radius)
                        } else {
                            if (question.selection[j]?.selectionText != "") {
                                answerList.add(question.selection[j]?.selectionText.toString())
                            } else {
                                answerList.add(question.selection[j]?.image.toString())
                            }
                            child.setBackgroundResource(R.drawable.shape_bg_rounded_corner_multiple_choice_green_20_full_radius)
                        }
                    }
                }

                var correct = 0
                for (j in question.selectionAnswer?.indices!!) {
                    for (k in answerList.indices) {
                        if (question.selection[j]?.selectionText != "") {
                            if (question.selectionAnswer[j]?.selectionText == answerList[k]) {
                                correct++
                            }
                        } else {
                            if (question.selectionAnswer[j]?.image == answerList[k]) {
                                correct++
                            }
                        }
                    }
                }

                val answer = AnswerModel(
                    id = Utility.getRandomString().toInt(),
                    tryoutId = question.tryoutId,
                    questionId = question.questionId,
                    essay = false,
                    answer = answerList,
                    correct = correct == question.selectionAnswer.size && correct == answerList.size
                )
                viewModel.postAnswer(answer)
                tabLayoutView(currentItem!!)
            }

            binding?.multipleChoiceContainer?.addView(linearLayout)
        }
    }

    private fun tabLayoutView(currentItem: Int) {
        val tabView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.tab_title, null) as TextView
        tabView.setBackgroundResource(R.drawable.shape_bg_rounded_corner_tab_answered_green_full_radius)
        tabView.setTextColor(requireContext().getColor(R.color.white))

        val currentTab = tabLayout.getTabAt(currentItem)
        currentTab?.customView = tabView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}