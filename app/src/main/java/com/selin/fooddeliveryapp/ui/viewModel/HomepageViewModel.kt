package com.selin.fooddeliveryapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.data.repo.FoodsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(private val frepo: FoodsRepo): ViewModel(){
    private val foodsList = MutableLiveData<List<Foods>>()
    val filteredFoods = MutableLiveData<List<Foods>>()

    init{
        getAllFoods()
    }

    private fun getAllFoods(){
        viewModelScope.launch {
            try {
                val allFoods = frepo.getAllFoods()
                foodsList.value = allFoods
                filteredFoods.value = allFoods
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun searchFoods(query: String) {
        val allFoods = foodsList.value ?: emptyList()
        val filteredResults = allFoods.filter { food ->
            food.yemek_adi.contains(query, ignoreCase = true)
        }
        filteredFoods.value = filteredResults
    }
}