package com.gosty.tryoutapp.ui.tryout.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.ActivityProblemBinding

class ProblemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProblemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProblemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val range = 1..10
        val test = range.toList()
        binding.viewPager.adapter = TabPagerProblemAdapter(this, 10)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = test[position].toString()
        }.attach()

        for (i in 0..9) {
            val tv = LayoutInflater.from(this@ProblemActivity).inflate(R.layout.tab_title, null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = tv
        }
    }
}