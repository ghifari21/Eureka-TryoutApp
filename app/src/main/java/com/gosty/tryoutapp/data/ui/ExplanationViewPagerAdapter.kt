package com.gosty.tryoutapp.data.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.ui.explanation.ExplanationFragment
import com.gosty.tryoutapp.ui.tryout.problem.singlechoice.SingleChoiceFragment

class ExplanationViewPagerAdapter(
    activity : AppCompatActivity,
    private val tryOutModel: List<TryoutModel?>?,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tryOutModel?.get(0)?.question?.size!! + tryOutModel?.get(1)?.question?.size!!

    override fun createFragment(position: Int): Fragment {
        val fragment = ExplanationFragment()
        val bundle = Bundle()
        if (tryOutModel?.get(0)?.id == 30){
            bundle.putParcelable(ExplanationFragment.EXTRA_DATA_EXPLANATION, tryOutModel?.get(0)!!.question?.get(position))
        } else if (tryOutModel?.get(1)?.id == 31){
            bundle.putParcelable(ExplanationFragment.EXTRA_DATA_EXPLANATION, tryOutModel?.get(1)!!.question?.get(position))
        }

        fragment.arguments = bundle

        return fragment
    }

}