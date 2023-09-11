package com.gosty.tryoutapp.ui.score

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.FragmentProfileBinding
import com.gosty.tryoutapp.databinding.FragmentScoreBinding
import com.gosty.tryoutapp.databinding.LayoutErrorScoreBinding
import com.gosty.tryoutapp.ui.explanation.ExplanationActivity
import com.kennyc.view.MultiStateView

class ScoreFragment : Fragment(), MultiStateView.StateListener {
    private var _binding : FragmentScoreBinding? = null
    private var _bindingStateError : LayoutErrorScoreBinding? = null
    private val binding get() = _binding
    private val bindingStateError get() = _binding
    private lateinit var multiStateView: MultiStateView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreBinding.inflate(inflater,container,false)
        _bindingStateError = LayoutErrorScoreBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multiStateView = binding?.msvScore!!
        multiStateView.listener = this@ScoreFragment

        multiStateView.viewState = MultiStateView.ViewState.EMPTY

//        binding?.button?.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    ExplanationActivity::class.java
//                )
//            )
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}
}