package com.gosty.tryoutapp.ui.tryout.problem

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@AndroidEntryPoint
class ProblemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProblemBinding
    private val viewModel: ProblemViewModel by viewModels()
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProblemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data = intent.getParcelableExtra<TryoutModel?>(EXTRA_TRYOUT_TYPE)
        val totalQuestion = intent.getIntExtra(EXTRA_TOTAL_QUESTIONS, 0)

        setupTimer(data)

        initView(data, totalQuestion)
    }

    private fun setupTimer(data: TryoutModel?) {
        timer = object : CountDownTimer(TIME_MILLISECONDS, TIME_ELAPSE) {
            override fun onTick(remaining: Long) {
                totalTimeSpent = TIME_MILLISECONDS - remaining
                val time = remaining.toDuration(DurationUnit.MILLISECONDS)
                val timeString = time.toComponents { minutes, seconds, _ ->
                    String.format("%02d:%02d", minutes, seconds)
                }
                binding.tvTimer.text = timeString
            }

            override fun onFinish() {
                totalTimeSpent = TIME_MILLISECONDS
                val intent = Intent(this@ProblemActivity, TryoutDoneActivity::class.java)
                intent.putExtra(TryoutDoneActivity.EXTRA_TRYOUT_CATEGORY, data?.categoryName)
                intent.putExtra(TryoutDoneActivity.EXTRA_TOTAL_QUESTIONS, data?.question?.size)
                intent.putExtra(TryoutDoneActivity.EXTRA_TOTAL_TIME_SPENT, totalTimeSpent)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initView(data: TryoutModel?, totalQuestion: Int) {
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
            intent.putExtra(TryoutDoneActivity.EXTRA_TOTAL_QUESTIONS, totalQuestion)
            intent.putExtra(TryoutDoneActivity.EXTRA_TOTAL_TIME_SPENT, totalTimeSpent)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    companion object {
        const val EXTRA_TRYOUT_TYPE = "tryout_type"
        const val EXTRA_TOTAL_QUESTIONS = "total_question"
        private const val TIME_MILLISECONDS: Long = 1_800_000
        private const val TIME_ELAPSE: Long = 1_000
        private var totalTimeSpent: Long = 0
    }
}