package com.gosty.tryoutapp.data.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.ui.explanation.ExplanationFragment

class ExplanationViewPagerAdapter(activity : AppCompatActivity, private val length : Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = length

    override fun createFragment(position: Int): Fragment {
        return ExplanationFragment()
    }

}