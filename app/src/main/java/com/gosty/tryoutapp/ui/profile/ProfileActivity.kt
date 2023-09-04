package com.gosty.tryoutapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.ActivityProfileBinding
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity(), MultiStateView.StateListener {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var multiStateView: MultiStateView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        multiStateView = binding.msvProfile
        multiStateView.listener = this

        binding.btnLogout.setOnClickListener {  }

    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}
}