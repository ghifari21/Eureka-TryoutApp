package com.gosty.tryoutapp.ui.tryout_done

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.databinding.ActivityTryoutDoneBinding
import com.gosty.tryoutapp.ui.main.MainActivity
import com.gosty.tryoutapp.utils.Result
import com.gosty.tryoutapp.utils.Utility
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class TryoutDoneActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTryoutDoneBinding
    private val viewModel: TryoutDoneViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.getAllUserAnswer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTryoutDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data = intent.getStringExtra(EXTRA_TRYOUT_CATEGORY)
        val time = intent.getLongExtra(EXTRA_TOTAL_TIME_SPENT, 0)
        val totalQuestion = intent.getIntExtra(EXTRA_TOTAL_QUESTIONS, 0)

        calculateAnswers(data, time, totalQuestion)
    }

    private fun calculateAnswers(data: String?, time: Long, totalQuestion: Int) {
        viewModel.answers.observe(this@TryoutDoneActivity) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.stateView.viewState = MultiStateView.ViewState.LOADING
                }

                is Result.Success -> {
                    binding.stateView.viewState = MultiStateView.ViewState.CONTENT

                    var correctAnswers = 0
                    var wrongAnswers = 0
                    val notAnswered = totalQuestion - result.data.size
                    for (i in result.data) {
                        if (i.correct == true) {
                            correctAnswers++
                        } else {
                            wrongAnswers++
                        }
                    }

                    val scoreModel = ScoreModel(
                        scoreId = Utility.getRandomString(),
                        correctAnswer = correctAnswers,
                        wrongAnswer = wrongAnswers,
                        notAnswered = notAnswered,
                        totalTime = time,
                        dateTime = Date().time,
                        score = correctAnswers * 10,
                        tryoutCategory = data,
                        answers = result.data
                    )

                    viewModel.postScore(scoreModel)

                    binding.btnViewScore.setOnClickListener {
                        val intent = Intent(this@TryoutDoneActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                is Result.Error -> {
                    binding.stateView.viewState = MultiStateView.ViewState.ERROR
                    val error = binding.stateView.getView(MultiStateView.ViewState.EMPTY)
                    if (error != null) {
                        val btn: Button = error.findViewById(R.id.btnRefreshProblem)
                        btn.setOnClickListener {
                            viewModel.getAllUserAnswer()
                        }
                    }
                }
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getAllUserAnswer()
            binding.refreshLayout.isRefreshing = false
        }
    }

    companion object {
        const val EXTRA_TRYOUT_CATEGORY = "tryout_category"
        const val EXTRA_TOTAL_QUESTIONS = "total_questions"
        const val EXTRA_TOTAL_TIME_SPENT = "total_time_spent"
    }
}