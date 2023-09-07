package com.gosty.tryoutapp.ui.explanation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.QuestionModel
import com.gosty.tryoutapp.data.ui.ExplanationViewPagerAdapter
import com.gosty.tryoutapp.databinding.ActivityExplanationBinding
import com.gosty.tryoutapp.ui.tryout.problem.ProblemViewModel
import com.gosty.tryoutapp.utils.Result
import com.kennyc.view.MultiStateView
import kotlinx.coroutines.flow.merge

class ExplanationActivity : AppCompatActivity(), MultiStateView.StateListener {
    private lateinit var binding : ActivityExplanationBinding
    private val viewModel: ExplanationViewModel by viewModels()
    private lateinit var multiStateView: MultiStateView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplanationBinding.inflate(layoutInflater)
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

        viewModel.getSubjectForExplanation().observe(this){
            when(it){
                is Result.Loading -> {
                    multiStateView.viewState = MultiStateView.ViewState.LOADING
                }
                is  Result.Error -> {
                    multiStateView.viewState = MultiStateView.ViewState.ERROR
                }
                is Result.Success -> {
                    val totalQuestion : Int = it.data[0].tryout?.get(0)?.question?.size!! + it.data[0].tryout?.get(1)?.question?.size!!
                    val range = 1..totalQuestion
                    val tabTitle = range.toList()
                    TabLayoutMediator(binding.tlExplanation, binding.vpExplanation) { tab, position ->
                        tab.text = tabTitle[position].toString()
                    }.attach()
                    binding.vpExplanation.apply {
                        adapter = ExplanationViewPagerAdapter(this@ExplanationActivity, it.data[0].tryout)
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
                    if (it.data[0].tryout?.get(0)?.id == 30){
                        binding.tvQuestionType.text = it.data[0].tryout?.get(0)?.categoryName
                    } else if (it.data[0].tryout?.get(1)?.id == 31){
                        binding.tvQuestionType.text = it.data[0].tryout?.get(1)?.categoryName
                    }
                    for (i in range) {
                        val tv = LayoutInflater.from(this@ExplanationActivity)
                            .inflate(R.layout.tab_title_explanation, null) as TextView
                        binding.tlExplanation.getTabAt(i)?.customView = tv
                    }
                }
            }
        }
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}
}