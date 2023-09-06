package com.gosty.tryoutapp.data.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.ui.tryout.problem.singlechoice.SingleChoiceFragment

class TabPagerProblemAdapter constructor(
    activity: AppCompatActivity,
    private val questions: List<QuestionModel?>?
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = questions?.size!!

    override fun createFragment(position: Int): Fragment {
        val fragment = SingleChoiceFragment()
        val bundle = Bundle()
        bundle.putParcelable(SingleChoiceFragment.EXTRA_DATA, questions?.get(position))
        fragment.arguments = bundle

        return fragment
    }
}