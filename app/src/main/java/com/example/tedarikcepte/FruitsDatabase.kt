/*
package com.example.tedarikcepte

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val database_name_fruits = "TedarikCepte"
val table_name_fruits = "Fruits"

val col_id_fruits = "id"
val col_category_fruits = "kategori"
val col_price_fruits = "fiyat"
val col_number_fruits = "adet"

class FruitsDatabase (var context: Context): SQLiteOpenHelper(context, database_name_fruits, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = " CREATE TABLE " + table_name_fruits + "(" +
                col_id_fruits + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_category_fruits + " VARCHAR(256)," +
                col_price_fruits + " VARCHAR(256)," +
                col_number_fruits + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getFruits() {
    }

    fun insertData(){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_category_fruits, "elma")
        cv.put(col_price_fruits, "18")
        cv.put(col_number_fruits, "5")

        db.insert(table_name_users, null, cv)

    }

}*/
