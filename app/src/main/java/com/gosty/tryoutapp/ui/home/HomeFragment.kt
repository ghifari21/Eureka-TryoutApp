package com.gosty.tryoutapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.FragmentHomeBinding
import com.gosty.tryoutapp.ui.auth.AuthActivity
import com.gosty.tryoutapp.ui.tryout.choice.ChoiceActivity
import com.gosty.tryoutapp.utils.MyTagHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = auth.currentUser
        if (user != null) {
            binding?.apply {
                Glide.with(requireContext())
                    .load(user.photoUrl)
                    .placeholder(R.drawable.icon_black_image_placeholder)
                    .error(R.drawable.icon_black_broken_image)
                    .centerCrop()
                    .into(ivPhoto)

                tvUserName.text = user.displayName
            }
        } else {
            startActivity(
                Intent(
                    requireContext(),
                    AuthActivity::class.java
                )
            )
        }

        binding?.tvRuleContent?.text =
            Html.fromHtml(getString(R.string.rule_content), null, MyTagHandler())

        binding?.btnStart?.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    ChoiceActivity::class.java
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}