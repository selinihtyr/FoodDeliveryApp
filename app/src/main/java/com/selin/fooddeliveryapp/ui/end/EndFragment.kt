package com.selin.fooddeliveryapp.ui.end

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.selin.fooddeliveryapp.databinding.FragmentEndBinding

class EndFragment : Fragment() {
    private lateinit var binding: FragmentEndBinding
    private val viewModel: EndViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe()
    }

    private fun observe() {
        viewModel.startSplash()
        viewModel.endCompleted.observe(viewLifecycleOwner) {
            if (it) {
                Handler().postDelayed({
                    activity?.finish()
                }, 5000)
            }
        }
    }
}