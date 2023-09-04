package com.gosty.tryoutapp.ui.explanation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.ActivityExplanationBinding

class ExplanationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExplanationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplanationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}