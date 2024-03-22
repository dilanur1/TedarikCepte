package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity: AppCompatActivity()  {

    lateinit var sessionManagement: SessionManagement
    lateinit var tv_username : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sessionManagement = SessionManagement(this)

        tv_username = findViewById(R.id.username)
        val username = intent.getStringExtra("username")
        tv_username.setText(username)

        val signOutButton : RelativeLayout = findViewById(R.id.signOutLayout)
        signOutButton.setOnClickListener{
            logOut()
        }
    }

    override fun onStart() {
        super.onStart()

        sessionManagement = SessionManagement(this)
        val user = sessionManagement.getUser()
        tv_username.text = user["username"].toString()
    }


    fun goToLoginActivity() {
        val goToLoginActivity = Intent(this, LoginActivity::class.java)
        goToLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(goToLoginActivity)
        finish()
    }

    fun logOut() {
        sessionManagement.removeSession()
        tv_username.setText(null)
        sessionManagement.logout()
        goToLoginActivity()
    }
}