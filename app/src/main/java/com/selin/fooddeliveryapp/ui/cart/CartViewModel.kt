package com.selin.fooddeliveryapp.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val foodRepo: FoodRepo
) : ViewModel() {
    val list = MutableLiveData<List<FoodCartResponse>>()
    val totalPrice = MutableLiveData<Int>()

    fun getCartList() {
        viewModelScope.launch(Dispatchers.IO) {
            val cartList = foodRepo.getCartFoods(username = AppConstants.USERNAME)

            var totalPrice = 0
            for (cartItem in cartList) {
                totalPrice += cartItem.price * cartItem.orderQuantity
            }

            this@CartViewModel.list.postValue(cartList)
            this@CartViewModel.totalPrice.postValue(totalPrice)
        }
    }

    fun delete(cartFoodId: Int, username: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            foodRepo.deleteFoodFromCart(cartFoodId, username)
            callback()
        }
    }
}
