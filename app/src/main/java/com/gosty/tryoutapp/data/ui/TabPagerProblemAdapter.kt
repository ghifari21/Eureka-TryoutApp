package com.gosty.tryoutapp.data.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.ui.tryout.problem.singlechoice.SingleChoiceFragment

class TabPagerProblemAdapter constructor(
    activity: AppCompatActivity,
    private val len: Int
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = len

    override fun createFragment(position: Int): Fragment {
        return SingleChoiceFragment()
    }
}