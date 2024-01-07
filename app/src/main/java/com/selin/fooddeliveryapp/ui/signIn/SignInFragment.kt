package com.selin.fooddeliveryapp.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun initViews() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.signInToSignUp)
        }
    }

    private fun observe() = with(binding) {
        viewModel.checkUserInfo()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { error: SignInError ->
                val stringResourceId = SignInError.toStringResource(error)
                Snackbar.make(requireView(), stringResourceId, Snackbar.LENGTH_SHORT).show()
            }
        }
        btnSignIn.setOnClickListener {
            val email = etInEmail.text.toString()
            val password = etInPassword.text.toString()
            viewModel.signIn(email, password)
        }
    }
}