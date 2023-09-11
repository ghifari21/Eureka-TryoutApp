package com.gosty.tryoutapp.ui.tryout.problem.essay

import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
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
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

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

        tabLayout = requireActivity().findViewById(R.id.tab_layout)
        viewPager = requireActivity().findViewById(R.id.view_pager)

        val imageList = mutableListOf<String>()
        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY
        binding?.mvQuestion?.text = Html.fromHtml(question?.questionText, flags, { source ->
            imageList.add(source.replace("""\""", ""))
            null
        }, null).toString()

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

                val answer = AnswerModel(
                    id = Utility.getRandomString().toInt(),
                    tryoutId = question.tryoutId,
                    questionId = question.questionId,
                    essay = true,
                    answer = listOf(essay),
                    correct = (essay in secondRange..firstRange) || essay == shortAnswer
                )
                viewModel.postAnswer(answer)
                tabLayoutView()
            }
        }
    }

    private fun tabLayoutView() {
        val tabView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.tab_title, null) as TextView
        tabView.setBackgroundResource(R.drawable.shape_bg_rounded_corner_tab_answered_green_full_radius)
        tabView.setTextColor(requireContext().getColor(R.color.white))

        val currentTab = tabLayout.getTabAt(viewPager.currentItem)
        currentTab?.customView = tabView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}