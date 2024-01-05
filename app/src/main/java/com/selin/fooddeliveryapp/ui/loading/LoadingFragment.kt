package com.selin.fooddeliveryapp.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {
    private lateinit var binding: FragmentLoadingBinding
    private lateinit var viewModel: LoadingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize the viewModel before calling observe
        viewModel = ViewModelProvider(this)[LoadingViewModel::class.java]
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
        viewModel.startLoading()
    }

    private fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}