package com.selin.fooddeliveryapp.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe()
    }

    private fun observe() {
        with(viewModel){
            startSplash()
            checkUserInfo()
            liveSplashCompleted.observe(viewLifecycleOwner) { completed ->
                if (completed) {
                    val destination = navigateToDestination(onBoardingFinished())
                    findNavController().navigate(destination)
                }
            }
        }
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}