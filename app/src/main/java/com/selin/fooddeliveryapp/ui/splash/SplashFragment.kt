package com.selin.fooddeliveryapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentSplashBinding

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
        viewModel.liveSplashCompleted.observe(viewLifecycleOwner) { splashCompleted ->
            if (splashCompleted) {
                if (isUserLoggedIn()) {
                    findNavController().navigate(R.id.homepageFragment)
                } else {
                    findNavController().navigate(R.id.signInFragment)
                }
            }
        }
        viewModel.startSplash()
    }

    private fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}