package com.gosty.tryoutapp.ui.tryout_done

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gosty.tryoutapp.data.models.TryoutModel
import com.gosty.tryoutapp.databinding.ActivityTryoutDoneBinding
import com.gosty.tryoutapp.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TryoutDoneActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTryoutDoneBinding
    private val viewModel: TryoutDoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTryoutDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data = intent.getStringExtra(EXTRA_TRYOUT_CATEGORY)

    }

    companion object {
        const val EXTRA_TRYOUT_CATEGORY = "tryout_category"
    }
}