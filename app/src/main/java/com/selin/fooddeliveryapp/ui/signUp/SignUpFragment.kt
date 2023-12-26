package com.selin.fooddeliveryapp.ui.signUp

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
import com.selin.fooddeliveryapp.databinding.SignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: SignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun initViews() = with(binding) {
        btnSignUp.setOnClickListener {
            val email = etInEmail.text.toString()
            val password = etInPassword.text.toString()
            val confirmPassword = editTextTextPassword2.text.toString()

            viewModel.signUp(email, password, confirmPassword)
        }
        tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.signUpToSignIn)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { error: SignUpError ->
                val stringResourceId = SignUpError.toStringResource(error)
                Snackbar.make(requireView(), stringResourceId, Snackbar.LENGTH_SHORT).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToSignInScreen.collect {
                findNavController().navigate(R.id.signUpToSignIn)
            }
        }
    }
}


