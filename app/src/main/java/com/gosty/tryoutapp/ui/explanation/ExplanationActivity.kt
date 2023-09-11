package com.gosty.tryoutapp.ui.explanation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.ui.ExplanationViewPagerAdapter
import com.gosty.tryoutapp.databinding.ActivityExplanationBinding
import com.gosty.tryoutapp.databinding.LayoutErrorExplanationBinding
import com.gosty.tryoutapp.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExplanationActivity : AppCompatActivity(), MultiStateView.StateListener {
    private lateinit var binding : ActivityExplanationBinding
    private lateinit var bindingStateError : LayoutErrorExplanationBinding
    private val viewModel: ExplanationViewModel by viewModels()
    private lateinit var multiStateView: MultiStateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplanationBinding.inflate(layoutInflater)
        bindingStateError = LayoutErrorExplanationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        multiStateView = binding.msvExplanation
        multiStateView.listener = this

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
        viewModel.getSubjectForExplanation().observe(this@ExplanationActivity){
            when(it){
                is Result.Loading -> {
                    multiStateView.viewState = MultiStateView.ViewState.LOADING
                }
                is  Result.Error -> {
                    multiStateView.viewState = MultiStateView.ViewState.ERROR
                    bindingStateError.btnRefreshExplanation.setOnClickListener {

                    }
                }
                is Result.Success -> {
                    multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    val totalQuestion : Int = it.data[0].tryout?.get(0)?.question?.size!! + it.data[0].tryout?.get(1)?.question?.size!!
                    val range = 0..totalQuestion
                    val tabTitle = range.toList()
                    binding.vpExplanation.adapter = ExplanationViewPagerAdapter(this@ExplanationActivity, it.data[0].tryout)
                    TabLayoutMediator(binding.tlExplanation, binding.vpExplanation) { tab, position ->
                        tab.text = (tabTitle[position] + 1).toString()
                    }.attach()

                    binding.vpExplanation.isUserInputEnabled = false

                    binding.vpExplanation.registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback(){
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            binding.btnPrevious.setOnClickListener {
                                if (position != 0) {
                                    binding.vpExplanation.currentItem = position - 1
                                }
                            }
                            binding.btnNext.setOnClickListener {
                                if (position != 19){
                                    binding.vpExplanation.currentItem = position + 1
                                }
                            }

                            binding.btnNext.isVisible = position != 19
                            binding.btnPrevious.isVisible = position != 0

                            if (it.data[0].tryout?.get(0)?.id == 30){
                                binding.tvQuestionType.text = it.data[0].tryout?.get(0)?.categoryName
                            } else if (it.data[0].tryout?.get(1)?.id == 31){
                                binding.tvQuestionType.text = it.data[0].tryout?.get(1)?.categoryName
                            }
                        }
                    })
                    for (i in range) {
                        val tv = LayoutInflater.from(this@ExplanationActivity)
                            .inflate(R.layout.tab_title, null) as TextView
                        binding.tlExplanation.getTabAt(i)?.customView = tv
                    }
                }

                else -> {}
            }
        }
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}
}