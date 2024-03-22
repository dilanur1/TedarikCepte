package com.example.tedarikcepte

data class Vegetable(override var product_id: Long, override var category: String, override var imagePath: String, override var price: Double) : Product(product_id, category, imagePath, price)
