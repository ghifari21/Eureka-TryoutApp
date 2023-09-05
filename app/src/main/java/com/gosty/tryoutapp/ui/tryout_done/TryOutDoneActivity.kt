package com.gosty.tryoutapp.ui.tryout_done

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.ActivityTryOutDoneBinding

class TryOutDoneActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTryOutDoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTryOutDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}