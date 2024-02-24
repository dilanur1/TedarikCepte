package com.example.tedarikcepte

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper


val database_name = "TedarikCepte"
val table_name = "Fruits"

val col_id = "id"
val col_price = "fiyat"
val col_number = "adet"

class FruitsDatabase (var context: Context): SQLiteOpenHelper(context, database_name, null, 1) {
}