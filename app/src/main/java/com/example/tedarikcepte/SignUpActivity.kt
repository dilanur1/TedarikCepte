package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tedarikcepte.databinding.ActivityMainBinding
import com.example.tedarikcepte.databinding.LoginBinding
import com.example.tedarikcepte.databinding.SignUpBinding

class SignUpActivity: AppCompatActivity() {
    lateinit var binding: SignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context=this
        var db=DatabaseHelper(context)



        binding.kaydet.setOnClickListener{
            var ad = binding.ad.text.toString()
            var soyad = binding.soyad.text.toString()
            var firma = binding.firmaIsmi.text.toString()
            var adres = binding.adres.text.toString()
            var telefon = binding.telno.text.toString()
            var kullaniciAdi = binding.kullaniciAdiKayit.text.toString()
            var parola = binding.password.text.toString()

            if (kullaniciAdi.isNotEmpty() && adres.isNotEmpty() && telefon.isNotEmpty() && parola.isNotEmpty() && ad.isNotEmpty()) {

                var user = User(ad, soyad, firma, adres, telefon, kullaniciAdi, parola)
                db.insertData(user)

            } else {
                Toast.makeText(applicationContext,"Boş bırakılan alanları doldurunuz.",Toast.LENGTH_LONG).show()
            }

        }
}
    fun switchToLoginPage () {
        val loginPage = Intent(this, LoginActivity::class.java)
        startActivity(loginPage)
    }
}
