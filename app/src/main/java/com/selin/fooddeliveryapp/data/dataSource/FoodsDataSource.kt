package com.selin.fooddeliveryapp.data.dataSource

import android.util.Log
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.data.entity.FoodsCart
import com.selin.fooddeliveryapp.retrofit.FoodsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.EOFException

class FoodsDataSource(var fdao: FoodsApi) {
    suspend fun getAllFoods() : List<Foods> = withContext(Dispatchers.IO){
        return@withContext fdao.getAllFoods().foods
    }

    suspend fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet: Int,kullanici_adi: String){
        val answer = fdao.addFoodToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        if (answer.success == 1){
            Log.i("Yemek Sepete Ekle","Başarı : ${answer.success} - Mesaj : ${answer.message}")
        } else {
            Log.e("Yemek Sepete Ekle","Başarı : ${answer.success} - Mesaj : ${answer.message}")
        }
    }

    suspend fun getCartFoods(kullanici_adi: String) : List<FoodsCart> = withContext(Dispatchers.IO) {
        try {
            val call = fdao.getCartFoods(kullanici_adi)
            val response = call.execute()  // This is a synchronous call
            if (response.isSuccessful && response.body() != null) {
                return@withContext response.body()!!.foods
            } else {
                return@withContext emptyList()
            }
        } catch (e: EOFException) {
            return@withContext emptyList()
        } catch (e: Exception) {
            Log.e("FoodsDataSource", "Error fetching cart foods: ", e)
            return@withContext emptyList()
        }
    }

    suspend fun deleteFoodFromCart(sepet_yemek_id: String, kullanici_adi: String){
        val answer = fdao.deleteFoodFromCart(sepet_yemek_id,kullanici_adi)
        Log.i("Yemek Sepetten Sil","Başarı : ${answer.success} - Mesaj : ${answer.message}")
    }
}