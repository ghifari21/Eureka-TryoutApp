package com.gosty.tryoutapp.ui.tryout.problem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.data.ui.TabPagerProblemAdapter
import com.gosty.tryoutapp.databinding.ActivityProblemBinding
import com.gosty.tryoutapp.ui.tryout_done.TryoutDoneActivity
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

        val data = intent.getParcelableExtra<TryoutModel?>(EXTRA_TRYOUT_TYPE)

        initView(data)
    }

    private fun initView(data: TryoutModel?) {
        viewModel.deleteAllUserAnswer()

        val range = 0..data?.question?.size!!
        val rangeList = range.toList()
        binding.viewPager.adapter =
            TabPagerProblemAdapter(this, data.question)
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
                    if (position != data.question.size - 1) {
                        binding.viewPager.currentItem = position + 1
                    }
                }

                binding.btnForward.isEnabled = position != data.question.size - 1
                binding.btnBack.isEnabled = position != 0
                binding.btnSubmit.isEnabled = position == data.question.size - 1
            }
        })

        for (i in range) {
            val tv = LayoutInflater.from(this@ProblemActivity)
                .inflate(R.layout.tab_title, null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = tv
        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this@ProblemActivity, TryoutDoneActivity::class.java)
            intent.putExtra(TryoutDoneActivity.EXTRA_TRYOUT_CATEGORY, data.categoryName)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_TRYOUT_TYPE = "tryout_type"
    }
}