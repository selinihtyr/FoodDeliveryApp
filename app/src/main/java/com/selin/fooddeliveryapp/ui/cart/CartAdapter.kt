package com.selin.fooddeliveryapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.remote.FoodCart
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding

class CartAdapter(
    private var list: List<FoodCart>,
    private val cartCallback: CartCallback
) : RecyclerView.Adapter<CartViewHolder>() {

    interface CartCallback {
        fun onClickDelete(cart: FoodCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemViewCartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, cartCallback)
    }

    fun updateData(newData: List<FoodCart>) {
        list = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = list[position]
        holder.bind(cart)
        /*
                    c.ibTrash.setOnClickListener {
                        Snackbar.make(
                            it,
                            "Are you sure you want to delete from the cart?",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Yes") {
                                deleteFromCart(cart, position)
                            }.show()
         }*/
    }
}

