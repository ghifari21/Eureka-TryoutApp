package com.gosty.tryoutapp.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.gosty.tryoutapp.databinding.ActivityAuthBinding
import com.gosty.tryoutapp.ui.main.MainActivity
import com.gosty.tryoutapp.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    @Inject lateinit var googleSignInOptions: GoogleSignInOptions
    private val viewModel: AuthViewModel by viewModels()

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(AuthActivity::class.java.simpleName, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(AuthActivity::class.java.simpleName, "Google sign in failed", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.btnLogin.setOnClickListener {
            signIn()
        }
    }

    /***
    *   this method is to processing sign in of the user where the token is taken from resultLauncher
     *   @author Ghifari
     *   @since September 14th, 2023
    * */
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    /***
    *  this method is to get the token and processing login of the user with firebase
     *  @author Ghifari
     *  @since September 14th, 2023
    * */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModel.signIn(credential).observe(this@AuthActivity) {
            when (it) {
                is Result.Loading -> {
                    binding.stateView.viewState = MultiStateView.ViewState.LOADING
                }

                is Result.Success -> {
                    binding.stateView.viewState = MultiStateView.ViewState.CONTENT
                    startActivity(
                        Intent(
                            this@AuthActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()
                }

                is Result.Error -> {
                    binding.stateView.viewState = MultiStateView.ViewState.CONTENT
                    Toast.makeText(this@AuthActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}