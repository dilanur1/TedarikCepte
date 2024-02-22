package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tedarikcepte.databinding.LoginBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LoginActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val button: TextView =findViewById(R.id.ÜyeOl)
        button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })


        val db = DatabaseHelper(this)
        val loginButton: Button = findViewById(R.id.girisYap)
        val username: TextView = findViewById(R.id.UsernameField)
        val password: TextView = findViewById(R.id.PasswordField)

        loginButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

/*            if(db.checkUser(user)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Geçersiz kullanıcı adı ya da parola",Toast.LENGTH_LONG).show()
            }*/
        }


    }

}