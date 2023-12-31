package com.selin.fooddeliveryapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter

    private val viewModel: CartViewModel by viewModels()

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
        adapter = CartAdapter(
            list = mutableListOf(),
            cartCallback = object : CartAdapter.CartCallback {
                override fun onClickDelete(cart: FoodCartResponse) {
                    viewModel.delete(cart.id, cart.username) {
                        viewModel.getCartList()
                    }
                }
            })
    }

    private fun initViews() = with(binding) {
        btnConfirmCart.setOnClickListener {
            if (viewModel.totalPrice.value == 0) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.cart_empty),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.transitationEnd)
            }
        }

        ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        rvCart.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rvCart.adapter = adapter
    }

    private fun observe() {
        viewModel.list.observe(viewLifecycleOwner) { cartItems ->
            adapter.updateData(cartItems)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotalPrice.text = getString(R.string.total_price, totalPrice)
        }

        viewModel.getCartList()
    }
}