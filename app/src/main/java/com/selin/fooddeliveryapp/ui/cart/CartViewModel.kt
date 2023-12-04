package com.selin.fooddeliveryapp.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.model.remote.FoodCart
import com.selin.fooddeliveryapp.data.model.local.Username
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val foodRepo: FoodRepo
) : ViewModel() {
    val cartList = MutableLiveData<List<FoodCart>>()
    val totalPrice = MutableLiveData<Int>()

    fun updateCart() {
        val username = Username.username
        viewModelScope.launch(Dispatchers.IO) {
            val cartList = foodRepo.getCartFoods(username)

            var totalPrice = 0
            for (cartItem in cartList) {
                totalPrice += cartItem.cartFoodPrice * cartItem.cartFoodOrderQuantity
            }

            this@CartViewModel.cartList.postValue(cartList)
            this@CartViewModel.totalPrice.postValue(totalPrice)
        }
    }

    fun addFoodToCart(
        name: String,
        imageName: String,
        price: Int,
        orderQuantity: Int,
        username: String
    ) {
        viewModelScope.launch {
            foodRepo.addFoodToCart(
                name,
                imageName,
                price,
                orderQuantity,
                username
            )
        }
    }

    fun delete(cartFoodId: Int, username: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            foodRepo.deleteFoodFromCart(cartFoodId, username)
            callback()
        }
    }
}
