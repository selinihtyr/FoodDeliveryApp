package com.selin.fooddeliveryapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.data.repo.FoodsRepo
import com.selin.fooddeliveryapp.retrofit.FoodsDao
import com.selin.fooddeliveryapp.retrofit.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(var frepo: FoodsRepo): ViewModel(){
    val foodsList = MutableLiveData<List<Foods>>()
    val filteredFoods = MutableLiveData<List<Foods>>()

    init{
        getAllFoods()
    }

    fun getAllFoods(){
        CoroutineScope(Dispatchers.Main).launch {
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