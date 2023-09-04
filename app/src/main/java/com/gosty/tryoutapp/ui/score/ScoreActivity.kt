package com.gosty.tryoutapp.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}