package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity: AppCompatActivity()  {

    lateinit var tv_username : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        tv_username = findViewById(R.id.username)
        val username = intent.getStringExtra("username")
        tv_username.setText(username)

        val signOutButton : RelativeLayout = findViewById(R.id.signOutLayout)
        signOutButton.setOnClickListener{
            signOut()
        }
    }

    fun signOut() {
        tv_username.setText(null)

        val goToLoginActivity = Intent(this, LoginActivity::class.java)
        startActivity(goToLoginActivity)
        finish()
    }
}