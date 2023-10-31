package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentCartBinding
import com.selin.fooddeliveryapp.ui.adapter.CartAdapter
import com.selin.fooddeliveryapp.ui.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private var isCartSelected = false
    private var isHomeSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeTotalPrice()
        setClickListeners()
        return binding.root
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(requireContext(), mutableListOf(), viewModel)
        binding.rvShoppingCart.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rvShoppingCart.adapter = cartAdapter
    }

    private fun observeTotalPrice() {
        viewModel.foodsCartList.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateData(cartItems)
        }

        viewModel.mTotalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotal.text = getString(R.string.total_price, totalPrice)
        }
    }

    private fun setClickListeners() {
        binding.btnConfirmCart.setOnClickListener {
            if (viewModel.mTotalPrice.value == 0) {
                showToast(R.string.cart_empty)
            } else {
                navigateToTransitionEnd()
                showToast(R.string.order_confirmed)
            }
        }

        binding.ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.tbCart2.setOnClickListener {
            updateToggleButtonState(true, false)
            navigateToFragment(R.id.cartFragment)
        }

        binding.tbHome2.setOnClickListener {
            updateToggleButtonState(false, true)
            navigateToFragment(R.id.homepageFragment)
        }
    }

    private fun navigateToTransitionEnd() {
        navigateToFragment(R.id.transitationEnd)
    }

    private fun updateToggleButtonState(isCartSelected: Boolean, isHomeSelected: Boolean) {
        this.isCartSelected = isCartSelected
        this.isHomeSelected = isHomeSelected
        updateButtonBackground(binding.tbCart2, isCartSelected)
        updateButtonBackground(binding.tbHome2, isHomeSelected)
    }

    private fun updateButtonBackground(view: View, isSelected: Boolean) {
        view.setBackgroundResource(if (isSelected) R.drawable.tbutton_background else R.drawable.tbutton_background)
    }

    private fun showToast(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToFragment(destination: Int) {
        Navigation.findNavController(binding.btnConfirmCart).navigate(destination)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateCart()
    }
}