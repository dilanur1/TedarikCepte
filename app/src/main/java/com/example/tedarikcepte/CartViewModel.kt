package com.example.tedarikcepte

import androidx.lifecycle.ViewModel

class CartViewModel: ViewModel() {
    private val productList = mutableListOf<Product>()

    fun addProduct(product: Product) {
        System.out.println("productList1: $productList")
        productList.add(product)
        System.out.println("productList2: $productList")
    }

    fun removeProduct(product: Product) {
        productList.remove(product)
    }

    fun getProducts(): List<Product> {
        System.out.println("productList3: $productList")
        return productList.toList()
    }
}