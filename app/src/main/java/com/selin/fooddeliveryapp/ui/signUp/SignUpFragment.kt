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
import kotlinx.coroutines.launch

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

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collect { message ->
                Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() = with(binding) {
        btnSignUp.setOnClickListener {
            val email = etInEmail.text.toString()
            val password = etInPassword.text.toString()
            val confirmPassword = editTextTextPassword2.text.toString()

            if (password != confirmPassword) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.password_is_not_the_same),
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.signUp(email, password, confirmPassword) { success ->
                if (success) {
                    findNavController().navigate(R.id.signUpToSignIn)
                }
            }
        }
        tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.signUpToSignIn)
        }
    }
}

