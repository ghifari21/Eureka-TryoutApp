package com.gosty.tryoutapp.ui.tryout.problem.essay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.FragmentEssayBinding

class EssayFragment : Fragment() {
    private var _binding: FragmentEssayBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEssayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}