package com.selin.fooddeliveryapp.ui.detail

import androidx.lifecycle.MutableLiveData
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
    var quantity = MutableLiveData<Int>()
    var quantityText = MutableLiveData<Int>()

    init {
        quantity.value = 1
    }
    fun addToCart(name: String, imageName: String, price: Int, orderQuantity: Int, username: String) {
        viewModelScope.launch {
            val response = repo.addFoodToCart(name, imageName, price, orderQuantity, username)
            _message.emit(response.message)
        }
    }
    fun increaseOrderQuantity(){
        quantityText.value ?: 1
        val currentQuantity = quantity.value ?: 1
        val maxQuantity = 10
        if(currentQuantity < maxQuantity){
            quantity.value = currentQuantity + 1
        }
    }
    fun decreaseOrderQuantity(){
        val currentQuantity = quantity.value ?: 1
        if(currentQuantity > 1){
            quantity.value = currentQuantity - 1
        }
    }
}