package com.gosty.tryoutapp.ui.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.FragmentProfileBinding
import com.gosty.tryoutapp.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private var _binding : FragmentScoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreBinding.inflate(inflater,container,false)
        return binding.root
    }
}