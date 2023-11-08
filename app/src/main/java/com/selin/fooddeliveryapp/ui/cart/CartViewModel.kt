package com.selin.fooddeliveryapp.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.entity.FoodCart
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val foodRepo: FoodRepo
) : ViewModel() {
    val foodsCartList = MutableLiveData<List<FoodCart>>()
    val totalPrice = MutableLiveData<Int>()

    fun updateCart() {
        val username = Username.username
        viewModelScope.launch(Dispatchers.IO) {
            val cartList = foodRepo.getCartFoods(username)

            var totalPrice = 0
            for (cartItem in cartList) {
                totalPrice += cartItem.foodPrice * cartItem.foodOrderQuantity
            }

            foodsCartList.postValue(cartList)
            this@CartViewModel.totalPrice.postValue(totalPrice)
        }
    }

    fun addFoodToCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        username: String
    ) {
        viewModelScope.launch {
            foodRepo.addFoodToCart(
                foodName,
                foodImageName,
                foodPrice,
                foodOrderQuantity,
                username
            )
        }
    }

    fun delete(sepet_yemek_id: Int, kullanici_adi: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            foodRepo.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)
            callback()

        }
    }
}
