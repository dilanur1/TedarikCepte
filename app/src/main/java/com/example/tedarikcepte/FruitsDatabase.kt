package com.example.tedarikcepte

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val database_name_fruits = "TedarikCepte"
val table_name_fruits = "Fruits"

val col_id_fruits = "id"
val col_price_fruits = "fiyat"
val col_number_fruits = "adet"

class FruitsDatabase (var context: Context): SQLiteOpenHelper(context, database_name_fruits, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}