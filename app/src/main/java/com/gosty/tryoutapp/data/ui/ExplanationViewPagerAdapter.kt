package com.gosty.tryoutapp.data.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.ui.explanation.ExplanationFragment

class ExplanationViewPagerAdapter(
    activity : AppCompatActivity,
    private val tryOutModel: List<TryoutModel?>?,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 20

    override fun createFragment(position: Int): Fragment {
        val fragment = ExplanationFragment()
        val bundle = Bundle()

        if (position < 10 ){
            bundle.putParcelable(ExplanationFragment.EXTRA_DATA_EXPLANATION, tryOutModel?.get(0)!!.question?.get(position))
        } else if (position == 10){
            bundle.putParcelable(ExplanationFragment.EXTRA_DATA_EXPLANATION, tryOutModel?.get(0)!!.question?.get(position - 1))
        } else {
            val i = position - 11
            Log.e("ANDIERROR",position.toString())
            bundle.putParcelable(ExplanationFragment.EXTRA_DATA_EXPLANATION, tryOutModel?.get(1)!!.question?.get(i))
        }

        fragment.arguments = bundle

        return fragment
    }

}