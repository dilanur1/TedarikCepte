package com.example.tedarikcepte

data class Vegetable(override var category: String, override var imagePath: String, override var price: Double, override var number: Int) : Product(category, imagePath, price, number)
