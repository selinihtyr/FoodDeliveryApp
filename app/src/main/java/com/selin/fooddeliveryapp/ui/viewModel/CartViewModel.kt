package com.selin.fooddeliveryapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.entity.FoodsCart
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.data.repo.FoodsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(var frepo: FoodsRepo) : ViewModel(){
    var foodsCartList = MutableLiveData<List<FoodsCart>>()
    var mTotalPrice = MutableLiveData<Int>()

    fun updateCart() {
        val kullanici_adi = Username.username
        viewModelScope.launch(Dispatchers.IO) {
            val cartList = frepo.getCartFoods(kullanici_adi)

            var totalPrice = 0
            for (cartItem in cartList) {
                totalPrice += cartItem.yemek_fiyat * cartItem.yemek_siparis_adet
            }

            foodsCartList.postValue(cartList)
            mTotalPrice.postValue(totalPrice)
        }
    }

    fun addFoodToCart(yemek_adi: String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int, kullanici_adi: String){
        viewModelScope.launch {
            frepo.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
        }
    }

    fun delete(sepet_yemek_id: String, kullanici_adi: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            frepo.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)
            callback()
        }
    }
}
