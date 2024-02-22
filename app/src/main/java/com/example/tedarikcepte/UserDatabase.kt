package com.example.tedarikcepte

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name = "TedarikCepte"
val table_name = "Users"

val col_id = "id"
val col_name = "ad"
val col_surname = "soyad"
val col_firm = "firma"
val col_address = "adres"
val col_phone = "telefon"
val col_username = "kullaniciAdi"
val col_password = "parola"

class DatabaseHelper (var context: Context):SQLiteOpenHelper(context, database_name, null, 1) {
    //class SignUpActvitiy: AppCompatActivity()
    val signUpActivity = SignUpActivity()
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = " CREATE TABLE " + table_name + "(" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_name + " VARCHAR(256)," +
                col_surname + " VARCHAR(256)," +
                col_firm + " VARCHAR(256)," +
                col_address + " VARCHAR(1000)," +
                col_phone + " VARCHAR(11)," +
                col_username + " VARCHAR(256)," +
                col_password + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    val userAdded = false
    fun insertData(user:User){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name, user.ad)
        cv.put(col_surname, user.soyad)
        cv.put(col_firm, user.firma)
        cv.put(col_address, user.adres)
        cv.put(col_phone, user.telefon)
        cv.put(col_username, user.kullaniciAdi)
        cv.put(col_password, user.parola)

        var sonuc = db.insert(table_name, null, cv)

        if (sonuc == (-1).toLong()) {
            Toast.makeText(context, "Hata!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Kaydınız başarıyla tamamlandı.", Toast.LENGTH_LONG).show()
            signUpActivity.switchToLoginPage()
        }
    }
}