package com.gosty.tryoutapp.ui.tryout.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.ActivityProblemBinding
import com.gosty.tryoutapp.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProblemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProblemBinding
    private val viewModel: ProblemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProblemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        // TODO CHANGE THIS
        viewModel.getSubjects().observe(this@ProblemActivity) {
            when (it) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    val range = 0..it.data[0].tryout?.get(0)?.question?.size!!
                    val rangeList = range.toList()
                    binding.viewPager.adapter =
                        TabPagerProblemAdapter(this, it.data[0].tryout?.get(0)?.question)
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = (rangeList[position] + 1).toString()
                    }.attach()

                    binding.viewPager.isUserInputEnabled = false

                    binding.viewPager.registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            binding.btnBack.setOnClickListener {
                                if (position != 0) {
                                    binding.viewPager.currentItem = position - 1
                                }
                            }
                            binding.btnForward.setOnClickListener {
                                if (position != 9) {
                                    binding.viewPager.currentItem = position + 1
                                }
                            }

                            binding.btnForward.isEnabled = position != 9
                            binding.btnBack.isEnabled = position != 0
                            binding.btnSubmit.isEnabled = position == 9
                        }
                    })

                    for (i in range) {
                        val tv = LayoutInflater.from(this@ProblemActivity)
                            .inflate(R.layout.tab_title, null) as TextView
                        binding.tabLayout.getTabAt(i)?.customView = tv
                    }
                }

                is Result.Error -> {

                }
            }
        }
    }
}