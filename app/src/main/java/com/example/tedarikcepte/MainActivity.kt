package com.example.tedarikcepte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tv_username : TextView = findViewById(R.id.username)
        var tv_firm : TextView = findViewById(R.id.firm)

        val meyveButton: ImageView =findViewById(R.id.meyveIcon)
        meyveButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FruitActivitiy::class.java)
            startActivity(intent)
        })

        val sepetimButton: ImageButton =findViewById(R.id.sepetimButton)
        sepetimButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        })

        val hesabimLayout: LinearLayout =findViewById(R.id.hesabimLayout)
        hesabimLayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        })

        val sebzeButton: ImageView =findViewById(R.id.sebzeIcon)
        sebzeButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, VegetablesActivity::class.java)
            startActivity(intent)
        })

        fun getUsernameAndFirm(username: String, firm: String) {
            tv_username.setText(username)
            tv_firm.setText(firm)
        }
    }

    override fun onStart() {
        super.onStart()
    }


}