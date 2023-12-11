package com.selin.fooddeliveryapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: FoodRepo) : ViewModel() {
    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> get() = _message
    fun addFoodToCart(name: String, imageName: String, price: Int, orderQuantity: Int, username: String) {
        viewModelScope.launch {
            val response = repo.addFoodToCart(name, imageName.toInt(), price.toString(), orderQuantity, username)
            _message.emit(response.message)
        }
    }

    fun increaseOrderQuantity(orderQuantity: Int): Int {
        return orderQuantity + 1
    }

    fun decreaseOrderQuantity(orderQuantity: Int): Int {
        return if (orderQuantity > 1) {
            orderQuantity - 1
        } else {
            1
        }
    }
}