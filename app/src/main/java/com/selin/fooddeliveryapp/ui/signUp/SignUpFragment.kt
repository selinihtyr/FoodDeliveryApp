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
import com.selin.fooddeliveryapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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

            viewModel.signUp(
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.error.collect { error: SignUpError ->
                val stringResourceId = SignUpError.toStringResource(error)
                Snackbar.make(requireView(), stringResourceId, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}