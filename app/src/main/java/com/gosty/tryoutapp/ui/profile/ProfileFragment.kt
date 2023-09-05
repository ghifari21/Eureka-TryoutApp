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
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.FragmentExplanationBinding
import com.gosty.tryoutapp.databinding.FragmentProfileBinding
import com.gosty.tryoutapp.ui.auth.AuthActivity


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayDataToView()

        binding?.btnLogout?.setOnClickListener {
            showLogoutAlertDialog()
        }
    }

    /*
        this method is to display user data to these view:
        1. image view profile
        2. text view name
        @author Andi
        @since September 5th, 2023
     */
    private fun displayDataToView(){
        val user = Firebase.auth.currentUser
        if (user != null){
            binding?.let {
                Glide.with(requireContext()).load(user.photoUrl).placeholder(R.drawable.image_profile_placeholder).into(
                    it.civProfilePhoto)
                it.tvName.text = user.displayName
            }
        } else {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    /*
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
}