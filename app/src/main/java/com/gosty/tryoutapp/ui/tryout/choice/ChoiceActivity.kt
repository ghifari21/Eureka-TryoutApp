package com.gosty.tryoutapp.ui.tryout.choice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gosty.tryoutapp.databinding.ActivityChoiceBinding

class ChoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}