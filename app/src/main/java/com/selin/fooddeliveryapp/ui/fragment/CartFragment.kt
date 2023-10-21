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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        cartAdapter = CartAdapter(requireContext(), mutableListOf(), viewModel)  // Initialize adapter here

        binding.rvShoppingCart.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rvShoppingCart.adapter = cartAdapter
        viewModel.mTotalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotal.text = "$totalPrice₺"
        }
        binding.btnConfirmCart.setOnClickListener {
            if(viewModel.mTotalPrice.value == 0){
                Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Navigation.findNavController(it).navigate(R.id.transitationEnd)
            Toast.makeText(requireContext(), "Order is confirmed!", Toast.LENGTH_SHORT).show()
        }

        viewModel.foodsCartList.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateData(cartItems)
        }

        binding.ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.tbCart2.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.cartFragment)
        }
        binding.tbHome2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homepageFragment)
        }

        return binding.root
    }

    private var isCartSelected = false
    private var isHomeSelected = false

    private fun updateToggleButtonState() {
        if (isCartSelected) {
            binding.tbCart2.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbCart2.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }

        if (isHomeSelected) {
            binding.tbHome2.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbHome2.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateCart()
    }
}
