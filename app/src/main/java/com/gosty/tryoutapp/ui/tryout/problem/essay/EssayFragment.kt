package com.gosty.tryoutapp.ui.tryout.problem.essay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.FragmentEssayBinding
import com.gosty.tryoutapp.utils.Result
import com.gosty.tryoutapp.utils.Utility
import com.gosty.tryoutapp.utils.Utility.resizeImageHtml
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EssayFragment : Fragment() {
    private var _binding: FragmentEssayBinding? = null
    private val binding get() = _binding
    private val viewModel: EssayViewModel by viewModels()
    private lateinit var tabLayout: TabLayout

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
        val currentItem = arguments?.getInt(TabPagerProblemAdapter.CURRENT_ITEM)

        tabLayout = requireActivity().findViewById(R.id.tab_layout)

        // Handling image in question.
        binding?.mvQuestion?.text = question?.questionText?.resizeImageHtml()

        binding?.tvTotalProblem?.text = activity?.getString(R.string.number_of_problem, pos, total)

        binding?.etEssay?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            // Send user answer when edit test lose the focus.
            if (!hasFocus && binding?.etEssay?.text.toString().trim() != "") {
                val essay = binding?.etEssay?.text.toString().trim().replace(".", ",")
                val shortAnswerFirstRange =
                    question?.shortAnswer!![0]!!.firstRange?.replace(".", ",")
                val shortAnswerSecondRange =
                    question.shortAnswer[0]!!.secondRange?.replace(".", ",")
                val shortAnswer =
                    question.shortAnswer[0]!!.shortAnswerText?.replace(".", ",")

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

                val answer = AnswerModel(
                    id = Utility.getRandomString().toInt(),
                    tryoutId = question.tryoutId,
                    questionId = question.questionId,
                    essay = true,
                    answer = listOf(essay),
                    correct = (essay in secondRange..firstRange) || essay == shortAnswer
                )
                viewModel.postAnswer(answer)
                tabLayoutView(currentItem!!)
            }
        }

        errorHandler()
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