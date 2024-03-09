/*
package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder: RecyclerView.ViewHolder {

    private lateinit var addToCartBtn: Button
    private lateinit var category: TextView
    private lateinit var price: TextView
    private var productsInCartList = mutableListOf<Product>()
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewholder_list_product)

        addToCartBtn = findViewById(R.id.addToCartBtn)
        category = findViewById(R.id.category)
        price = findViewById(R.id.price)

        productsInCartList = ArrayList()


        addToCartBtn.setOnClickListener() {
            val categoryString = category.toString()
            val priceString = price.toString()
            val priceDouble = priceString.toDouble()
            product.category = categoryString
            product.price = priceDouble

            productsInCartList.add(product)

            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("productsInCartList", productsInCartList as ArrayList<Product>)
            startActivity(intent)
            finish()
        }



    }


}*/
