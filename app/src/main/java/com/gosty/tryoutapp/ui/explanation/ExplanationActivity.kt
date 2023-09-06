package com.gosty.tryoutapp.ui.explanation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.ui.ExplanationViewPagerAdapter
import com.gosty.tryoutapp.databinding.ActivityExplanationBinding

class ExplanationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExplanationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplanationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        init()
    }

    /*
        this method is to:
        1. set view pager adapter
        2. user unable to swipe explanation fragment instead of click previous-next as a navigation button
        3. set click listener for previous-next as a navigation button
        @author Andi
        @since September 6th, 2023
     */
    private fun init() {
        val range = 1..20
        val test = range.toList()
        TabLayoutMediator(binding.tlExplanation, binding.vpExplanation) { tab, position ->
            tab.text = test[position].toString()
        }.attach()
        binding.vpExplanation.apply {
            adapter = ExplanationViewPagerAdapter(this@ExplanationActivity, range.last)
            isUserInputEnabled = false
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.btnPrevious.apply {
                        if (position != 0) {
                            isEnabled = true
                            binding.vpExplanation.currentItem = position - 1
                        }
                    }
                    binding.btnNext.apply {
                        if (position != range.last - 1){
                            isEnabled = true
                            binding.vpExplanation.currentItem = position + 1
                        }
                    }
                }
            })
        }
    }
}