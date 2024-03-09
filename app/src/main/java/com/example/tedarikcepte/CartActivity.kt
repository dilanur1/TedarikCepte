package com.example.tedarikcepte

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class CartActivity: AppCompatActivity() {
    private lateinit var  backBtn: ImageView
    private  lateinit var recyclerView: RecyclerView
    private  lateinit var productsInCartAdapter: ProductsInCartAdapter
    private lateinit var cartViewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        backBtn = findViewById(R.id.backBtn)
        recyclerView = findViewById(R.id.cardView)
        goBack()
        initList()
        val category = intent.getStringExtra("fruitCategory")
        val price  = intent.getStringExtra("fruitPrice")

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]








        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = productsInCartAdapter
        if (category != null) {
            if (price != null) {
                addDataToCartList(category, price)
            }
        }
        val productsInCartList = cartViewModel.getProducts()
        productsInCartAdapter = ProductsInCartAdapter(this@CartActivity, productsInCartList, this)
        cartViewModel.getProducts()


    }

    private fun goBack() {
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun initList() {


    }

    private fun addDataToCartList(category: String, price: String) {
        /* var fruit: Fruit? = null

         var category: String = jsonObject.getString("category")
         val price: Double = jsonObject.getDouble("price")
         val number: Int = jsonObject.getInt("number")

         if(category == "üzüm") {category = "uzum"}*/


        val priceWithoutTL = price.split(" ")[0]
        var product: Product? = null
        product = Fruit(category, category, priceWithoutTL.toDouble(), "12".toInt())
        cartViewModel.addProduct(product)


/*
        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)

        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)
        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)
        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)

        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)
        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)
        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)
        product = Fruit("elma", "elma", "10".toDouble(), "12".toInt())
        productsInCartList.add(product)*/
        productsInCartAdapter!!.notifyDataSetChanged()
    }

    private fun getProductsInCart() {
        cartViewModel.getProducts()
    }


}


































