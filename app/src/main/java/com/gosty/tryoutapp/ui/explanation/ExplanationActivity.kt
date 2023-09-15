package com.gosty.tryoutapp.ui.explanation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.data.ui.ExplanationViewPagerAdapter
import com.gosty.tryoutapp.databinding.ActivityExplanationBinding
import com.gosty.tryoutapp.databinding.LayoutErrorExplanationBinding
import com.gosty.tryoutapp.ui.tryout.problem.ProblemActivity
import com.gosty.tryoutapp.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExplanationActivity : AppCompatActivity(), MultiStateView.StateListener {
    private lateinit var binding : ActivityExplanationBinding
    private val viewModel: ExplanationViewModel by viewModels()
    private lateinit var multiStateView: MultiStateView
    private lateinit var dataTryout : String
    private lateinit var tabLayout: TabLayout
    private lateinit var dataAnswer : ScoreModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplanationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        multiStateView = binding.msvExplanation
        multiStateView.listener = this

        dataTryout = intent.getStringExtra(EXTRA_QUESTION_TYPE)!!
        dataAnswer =intent.getParcelableExtra(EXTRA_ANSWER)!!

        init(checkTryoutCategory(), dataAnswer!!)

        multiStateView.getView(MultiStateView.ViewState.ERROR)?.findViewById<Button>(R.id.btnRefreshExplanation)?.setOnClickListener {
            init(checkTryoutCategory(),dataAnswer)
        }
    }

    /***
    *   this method is to:
    *   1. check the tryout category, "Data dan Ketidakpastian" or "Geometri dan Pengukuran"
    *   @author Andi
    *   @since September 11th, 2023
    * */
    private fun checkTryoutCategory() : Int{
        if (dataTryout.lowercase() == "Data dan Ketidakpastian".lowercase()){
            return 0
        } else {
            return  1
        }
    }

    /***
        this method is to:
        1. set view pager adapter
        2. user unable to swipe explanation fragment instead of click previous-next as a navigation button
        3. set click listener for previous-next button as a navigation button
        4. implement multi state view
        5. setup view pager
        6. setup tab layout
        7. setup custom view of tab layout
        @param tryoutIndex to get the index of tryout, if 30 it is Data dan Ketidakpastian, else (31) it is Geometri dan Pengukuran
        @author Andi
        @since September 6th, 2023
        Updated Septmber 11th, 2023 by Andi
     */
    private fun init(tryoutIndex : Int, answerData : ScoreModel) {
        viewModel.getSubjectForExplanation().observe(this@ExplanationActivity){
            when(it){
                is Result.Loading -> {
                    multiStateView.viewState = MultiStateView.ViewState.LOADING
                }
                is Result.Success -> {
                    multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    val range = 1..it.data[0].tryout?.get(tryoutIndex)?.question?.size!!
                    val tabTitle = range.toList()
                    val question = it.data[0].tryout?.get(tryoutIndex)?.question

                    binding.vpExplanation.adapter = ExplanationViewPagerAdapter(this@ExplanationActivity, it.data[0].tryout?.get(tryoutIndex)?.question, answerData)

                    TabLayoutMediator(binding.tlExplanation, binding.vpExplanation) { tab, position ->
                        tab.text = (tabTitle[position]).toString()
                        val tabView =
                            LayoutInflater.from(this).inflate(R.layout.tab_title_explanation, null) as TextView
                        val getQuestionId = question?.get(position)?.questionId
                        if (dataAnswer.answers != null){
                            for (i in dataAnswer.answers!!){
                                if (getQuestionId == i.questionId){
                                    if (i.correct == true){
                                        tabView.setBackgroundResource(R.drawable.shape_bg_rounded_corner_tab_answered_green_full_radius)
                                        tabView.setTextColor(this.getColor(R.color.white))
                                    } else {
                                        tabView.setBackgroundResource(R.drawable.shape_bg_rounded_corner_tab_answered_red_full_radius)
                                        tabView.setTextColor(this.getColor(R.color.white))
                                    }
                                }
                            }
                        }

                        tab.customView = tabView
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
                            binding.btnNext.setOnClickListener {view ->
                                if (position != it.data[0].tryout?.get(tryoutIndex)?.question?.size!! - 1){
                                    binding.vpExplanation.currentItem = position + 1
                                }
                            }

                            binding.btnNext.isVisible = position != it.data[0].tryout?.get(tryoutIndex)?.question?.size!! - 1
                            binding.btnPrevious.isVisible = position != 0

                            binding.tvQuestionType.text = it.data[0].tryout?.get(tryoutIndex)?.categoryName
                        }
                    })
                }
                is Result.Error -> {
                    multiStateView.viewState = MultiStateView.ViewState.ERROR
                    Toast.makeText(this@ExplanationActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}

    companion object {
        const val EXTRA_QUESTION_TYPE = "question_type"
        const val EXTRA_ANSWER = "answer"

    }
}