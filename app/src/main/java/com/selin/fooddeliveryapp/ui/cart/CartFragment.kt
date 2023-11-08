package com.selin.fooddeliveryapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.entity.FoodCart
import com.selin.fooddeliveryapp.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    private val viewModel: CartViewModel by viewModels()

    private var isCartSelected = false
    private var isHomeSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()
        initViews()
        observe()
    }

    private fun initVariables() {
        cartAdapter = CartAdapter(
            cartList = mutableListOf(),
            cartCallback = object : CartAdapter.CartCallback {
                override fun onClickDelete(cart: FoodCart) {
                    viewModel.delete(cart.cartFoodId, cart.username) {
                        viewModel.updateCart()
                    }
                }
            })
    }

    private fun initViews() = with(binding) {
        btnConfirmCart.setOnClickListener {
            if (viewModel.totalPrice.value == 0) {
                showToast(R.string.cart_empty)
            } else {
                navigateToTransitionEnd()
                showToast(R.string.order_confirmed)
            }
        }

        ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        tbCartCart.setOnClickListener {
            updateToggleButtonState(true, false)
            navigateToFragment(R.id.cartFragment)
        }

        tbHomeCart.setOnClickListener {
            updateToggleButtonState(false, true)
            navigateToFragment(R.id.homepageFragment)
        }

        rvShoppingCart.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rvShoppingCart.adapter = cartAdapter
    }

    private fun observe() {
        viewModel.foodsCartList.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateData(cartItems)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotalPrice.text = getString(R.string.total_price, totalPrice)
        }

        viewModel.updateCart()
    }

    private fun navigateToTransitionEnd() {
        navigateToFragment(R.id.transitationEnd)
    }

    private fun updateToggleButtonState(isCartSelected: Boolean, isHomeSelected: Boolean) {
        this.isCartSelected = isCartSelected
        this.isHomeSelected = isHomeSelected
        updateButtonBackground(binding.tbCartCart, isCartSelected)
        updateButtonBackground(binding.tbHomeCart, isHomeSelected)
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
}