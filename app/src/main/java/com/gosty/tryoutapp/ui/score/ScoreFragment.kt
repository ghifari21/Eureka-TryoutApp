package com.gosty.tryoutapp.ui.score


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.ui.ScoreRecyclerViewAdapter
import com.gosty.tryoutapp.databinding.FragmentScoreBinding
import com.gosty.tryoutapp.ui.home.HomeFragment
import com.gosty.tryoutapp.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : Fragment(), MultiStateView.StateListener {
    private var _binding : FragmentScoreBinding? = null
    private val binding get() = _binding
    private lateinit var multiStateView: MultiStateView
    private val viewModel: ScoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multiStateView = binding?.msvScore!!
        multiStateView.listener = this@ScoreFragment

        init()

        multiStateView.getView(MultiStateView.ViewState.ERROR)?.findViewById<Button>(R.id.btnRefreshScore)?.setOnClickListener {
            init()
        }

        multiStateView.getView(MultiStateView.ViewState.EMPTY)?.findViewById<Button>(R.id.btnMulaiTestPertama)?.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment)
            navController.navigate(
                R.id.action_navigation_score_to_navigation_home
            )
        }
    }


    /*
    *   this method is to:
    *   1. implement multi state view
    *   2. implement view model
    *   3. setup recycler view
    *   @author Andi
    *   @since September 11th, 2023
    * */
    private fun init(){
        viewModel.getListScore().observe(requireActivity()) {
            when(it){
                is Result.Loading -> multiStateView.viewState = MultiStateView.ViewState.LOADING
                is Result.Success -> {
                    if (it.data.isEmpty()){
                        multiStateView.viewState = MultiStateView.ViewState.EMPTY
                    } else {
                        binding?.rvScore?.apply {
                            adapter = ScoreRecyclerViewAdapter(it.data.reversed())
                            layoutManager = LinearLayoutManager(activity)
                            setHasFixedSize(true)
                        }
                        multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    }
                }
                is Result.Error -> {
                    multiStateView.viewState = MultiStateView.ViewState.ERROR
                    Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {}
}