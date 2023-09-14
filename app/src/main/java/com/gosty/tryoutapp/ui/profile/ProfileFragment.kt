package com.gosty.tryoutapp.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.FragmentExplanationBinding
import com.gosty.tryoutapp.databinding.FragmentProfileBinding
import com.gosty.tryoutapp.databinding.LayoutErrorProfileBinding
import com.gosty.tryoutapp.ui.auth.AuthActivity
import com.gosty.tryoutapp.utils.Converter
import com.gosty.tryoutapp.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(), MultiStateView.StateListener {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding
    private lateinit var multiStateView: MultiStateView
    private val viewModel : ProfileViewModel by viewModels()

    @Inject
    lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multiStateView = binding?.msvProfile!!
        multiStateView.listener = this@ProfileFragment

        init()

        binding?.btnLogout?.setOnClickListener {
            showLogoutAlertDialog()
        }

        multiStateView.getView(MultiStateView.ViewState.ERROR)?.findViewById<Button>(R.id.btnLoginProfile)?.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    /***
        this method is to :
        1. implement multi state view
        2. get user data auth
        3. implement view model
        4. dynamically make mean text view changed depend on it's value
        @author Andi
        @since September 5th, 2023
     */
    private fun init(){
        viewModel.getScoreData().observe(requireActivity()){result ->
            when(result){
                is Result.Loading -> multiStateView.viewState = MultiStateView.ViewState.LOADING
                is Result.Success -> {
                    val user = auth.currentUser
                    if (user != null){
                        binding?.let {
                            Glide.with(requireContext())
                                .load(user.photoUrl)
                                .placeholder(R.drawable.image_profile_placeholder)
                                .error(R.drawable.icon_black_broken_image)
                                .centerCrop()
                                .into(it.civProfilePhoto)
                            it.tvName.text = user.displayName
                            it.tvTotalTest.text = "0"
                            it.tvNilaiRataRata.text = "0"
                            it.tvTotalTest.text = result.data.size.toString()
                            if (result.data.isNotEmpty()) {
                                var sum : Int = 0
                                for (i in result.data){
                                    sum += i.score!!
                                }
                                val mean = sum/result.data.size
                                it.tvNilaiRataRata.text = mean.toString()
                                if (mean < 50) {
                                    it.tvNilaiRataRata.setTextAppearance(R.style.font_36_bold_red_DC2020)
                                } else if (mean < 80){
                                    it.tvNilaiRataRata.setTextAppearance(R.style.font_36_bold_yellow_E9A100)
                                } else {
                                    it.tvNilaiRataRata.setTextAppearance(R.style.font_36_bold_green_29823B)
                                }
                            }
                        }
                        multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    } else {
                        multiStateView.viewState = MultiStateView.ViewState.ERROR
                    }
                }
                is Result.Error -> {
                    multiStateView.viewState = MultiStateView.ViewState.ERROR
                    Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    /***
    *   this method is to show alert dialog for logout confirmation from the user.
    *   @author Andi
    *   @since September 5th, 2023
    * */
    private fun showLogoutAlertDialog(){
        val logoutAlertDialog = AlertDialog.Builder(requireContext())
            .setTitle(activity?.getString(R.string.alert_dialog_title))
            .setPositiveButton(activity?.getString(R.string.positive_btn),null)
            .setNegativeButton(activity?.getString(R.string.negative_btn),null)
            .create()

        logoutAlertDialog.show()

        logoutAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}
}