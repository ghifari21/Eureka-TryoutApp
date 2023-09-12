package com.gosty.tryoutapp.data.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.ui.tryout.problem.essay.EssayFragment
import com.gosty.tryoutapp.ui.tryout.problem.multiplechoice.MultipleChoiceFragment
import com.gosty.tryoutapp.ui.tryout.problem.singlechoice.SingleChoiceFragment

class TabPagerProblemAdapter constructor(
    activity: AppCompatActivity,
    private val questions: List<QuestionModel?>?
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = questions?.size!!

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = if (questions?.get(position)?.isEssay == true) {
            EssayFragment()
        } else {
            if (questions?.get(position)?.isMultipleAnswer == true) {
                MultipleChoiceFragment()
            } else {
                SingleChoiceFragment()
            }
        }

        val bundle = Bundle()
        bundle.putParcelable(EXTRA_DATA, questions?.get(position))
        bundle.putString(EXTRA_POS, (position + 1).toString())
        bundle.putString(EXTRA_TOTAL, questions?.size.toString())
        bundle.putInt(CURRENT_ITEM, position)
        fragment.arguments = bundle

        return fragment
    }

    companion object {
        const val EXTRA_DATA = "extra"
        const val EXTRA_POS = "position"
        const val EXTRA_TOTAL = "total"
        const val CURRENT_ITEM = "current_item"
    }
}