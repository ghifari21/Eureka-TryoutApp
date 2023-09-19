package com.gosty.tryoutapp.ui.tryout.problem.singlechoice

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.FragmentSingleChoiceBinding
import com.gosty.tryoutapp.utils.Result
import com.gosty.tryoutapp.utils.Utility
import com.gosty.tryoutapp.utils.Utility.resizeImageHtml
import dagger.hilt.android.AndroidEntryPoint
import io.github.kexanie.library.MathView

@AndroidEntryPoint
class SingleChoiceFragment : Fragment() {
    private var _binding: FragmentSingleChoiceBinding? = null
    private val binding get() = _binding
    private val viewModel: SingleChoiceViewModel by viewModels()
    private lateinit var tabLayout: TabLayout

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
        val currentItem = arguments?.getInt(TabPagerProblemAdapter.CURRENT_ITEM)

        tabLayout = requireActivity().findViewById(R.id.tab_layout)

        binding?.mvQuestion?.text = question?.questionText?.resizeImageHtml()

        answerHandler(question, currentItem)

        binding?.tvTotalProblem?.text = activity?.getString(R.string.number_of_problem, pos, total)

        errorHandler()
    }

    /***
     * This method is to handle answers that user can pick.
     * @param question contain question model
     * @param currentItem variable that indicate index of current page.
     * @author Ghifari Octaverin
     * @since Sept 11th, 2023
     */
    private fun answerHandler(question: QuestionModel?, currentItem: Int?) {
        var choice = 'A'
        for (i in question?.selection!!) {
            val linearLayout = LinearLayout(requireActivity())
            val linearLayoutParams =
                MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT)

            linearLayoutParams.setMargins(0, 0, 0, 16)
            linearLayout.layoutParams = linearLayoutParams

            linearLayout.setPadding(16)
            linearLayout.elevation = 2f
            linearLayout.setBackgroundResource(R.drawable.shape_bg_rounded_corner_multiple_choice_gray_20_full_radius)

            val imageView = ImageView(requireActivity())
            imageView.setBackgroundResource(R.drawable.icon_white_check_circle)
            val ivParams =
                LinearLayout.LayoutParams(
                    MarginLayoutParams.WRAP_CONTENT,
                    MarginLayoutParams.WRAP_CONTENT
                )
            ivParams.gravity = Gravity.CENTER_VERTICAL
            ivParams.setMargins(0, 0, 16, 0)
            imageView.layoutParams = ivParams
            linearLayout.addView(imageView)

            val textView = TextView(requireActivity())
            val tvLayout = LinearLayout.LayoutParams(
                MarginLayoutParams.WRAP_CONTENT,
                MarginLayoutParams.WRAP_CONTENT
            )
            tvLayout.gravity = Gravity.CENTER_VERTICAL
            tvLayout.setMargins(0, 0, 16, 0)
            textView.layoutParams = tvLayout
            textView.setTextColor(requireActivity().getColor(R.color.black))
            textView.text = "$choice."
            choice++
            linearLayout.addView(textView)

            if (i?.selectionText != "") {
                val mathView = MathView(requireActivity(), null)
                val mvLayoutParams = LinearLayout.LayoutParams(
                    MarginLayoutParams.WRAP_CONTENT,
                    MarginLayoutParams.WRAP_CONTENT
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
                for (j in 0..<binding?.multipleChoiceContainer?.childCount!!) {
                    val child = binding?.multipleChoiceContainer?.getChildAt(j)
                    question.selection[j]?.isAnswered = false
                    if (child is LinearLayout) {
                        child.setBackgroundResource(R.drawable.shape_bg_rounded_corner_multiple_choice_gray_20_full_radius)
                    }
                    linearLayout.setBackgroundResource(R.drawable.shape_bg_rounded_corner_multiple_choice_green_20_full_radius)
                }
                i?.isAnswered = true

                val answers = if (i?.selectionText != "") {
                    listOf(i?.selectionText.toString())
                } else {
                    listOf(i.image.toString())
                }
                val answer = AnswerModel(
                    id = Utility.getRandomString().toInt(),
                    tryoutId = question.tryoutId,
                    questionId = question.questionId,
                    essay = false,
                    answer = answers,
                    correct = answers[0] == question.selectionAnswer?.get(0)!!.selectionText || answers[0] == question.selectionAnswer[0]!!.image
                )
                viewModel.postAnswer(answer)
                tabLayoutView(currentItem!!)
            }

            binding?.multipleChoiceContainer?.addView(linearLayout)
        }
    }

    /***
     * This method is to handle error message.
     * @author Ghifari Octaverin
     * @since Sept 14th, 2023
     */
    private fun errorHandler() {
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> Toast.makeText(
                    requireActivity(),
                    "Error, your answer may not saved: ${result.error}",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {}
            }
        }
    }

    /***
     * This method to handle tab layout background as indicator user already answered the question.
     * @param currentItem variable that indicate index of current page.
     * @author Ghifari Octaverin
     * @since Sept 11th, 2023
     */
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