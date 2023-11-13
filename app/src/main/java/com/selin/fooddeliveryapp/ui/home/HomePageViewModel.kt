package com.selin.fooddeliveryapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.entity.Food
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.retrofit.FoodApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val foodRepo: FoodRepo,
    private val foodApi: FoodApi
) : ViewModel() {
    private val foodsList = MutableLiveData<List<Food>>()
    val filteredFoods = MutableLiveData<List<Food>>()
    val showMessage = MutableSharedFlow<String>()

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            try {
                val allFoods = foodRepo.getAllFoods()
                foodsList.value = allFoods
                filteredFoods.value = allFoods
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToCart(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = foodApi
                .addFoodToCart(
                    food.name,
                    food.imageName,
                    food.price.toInt(),
                    1,
                    Username.username
                )
            if (response.success == 1) {
                showMessage.emit("Item added to cart successfully")
            }
        }
    }

    fun searchFoods(query: String) {
        val allFoods = foodsList.value ?: emptyList()
        val filteredResults = allFoods.filter { food ->
            food.name.contains(query, ignoreCase = true)
        }
        filteredFoods.value = filteredResults
    }
}