package com.gosty.tryoutapp.ui.tryout.choice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gosty.tryoutapp.databinding.ActivityChoiceBinding
import com.gosty.tryoutapp.ui.tryout.problem.ProblemActivity

class ChoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnDataDanKetidakpastian.setOnClickListener {
            startActivity(
                Intent(
                    this@ChoiceActivity,
                    ProblemActivity::class.java
                )
            )
        }
    }
}