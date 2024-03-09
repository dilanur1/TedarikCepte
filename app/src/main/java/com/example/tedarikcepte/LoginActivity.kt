package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class LoginActivity: AppCompatActivity() {

    private val LOGIN_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerButton: TextView = findViewById(R.id.kayıtOlTxt)
        val loginButton: Button = findViewById(R.id.loginButton)
        val usernameField: EditText = findViewById(R.id.usernameField)
        val passwordField: EditText = findViewById(R.id.passwordField)
        val passwordShowIcon: ImageView = findViewById(R.id.passwordShowIcon)
        val passwordHideIcon: ImageView = findViewById(R.id.passwordHideIcon)


        fun goToSignUpActivity() {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun goToMainActivity() {
            val goToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(goToMainActivity)
            //onActivityResult()
            finish()
        }


        fun validateUsername(): Boolean {
            val username = usernameField.text.toString()
            if(username.isEmpty()) {
                usernameField.setError("Lütfen kullanıcı adını giriniz.")
                return false
            }else {
                usernameField.setError(null)
                return true
            }
        }

        fun validatePassword(): Boolean {
            val password = passwordField.text.toString()
            if(password.isEmpty()) {
                passwordField.setError("Lütfen parola giriniz.")
                return false
            }else {
                passwordField.setError(null)
                return true
            }
        }

        fun authenticateUser() {
            if( !validateUsername() || !validatePassword()) {
                return
            }

            val  requestQueue = Volley.newRequestQueue(this)
            val url = "http://192.168.56.1:8080/api/v1/user/login"

            val params = HashMap<String, String>()
            params.put("username", usernameField.text.toString())
            params.put("password", passwordField.text.toString())

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, JSONObject(
                params as Map<String, String>
            ),
                Response.Listener<JSONObject> {response: JSONObject ->
                    try {

                        val username = response.get("username").toString()
                        val firm = response.get("firm").toString()

                        val goToProfile = Intent(this, ProfileActivity::class.java)
                        goToMainActivity()




                        /*goToProfile.putExtra("username", username)
                        startActivity(goToProfile)
                        finish()*/

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        System.out.println(e)
                    }

            }, Response.ErrorListener {error ->
                    error.printStackTrace()
                    System.out.println(error)
                    Toast.makeText(this, "Kullanıcı adı veya şifre geçersiz!", Toast.LENGTH_LONG).show()
            })

            requestQueue.add(jsonObjectRequest)
        }


        loginButton.setOnClickListener{
            authenticateUser()
        }

        registerButton.setOnClickListener{
            goToSignUpActivity()
        }

        passwordShowIcon.setOnClickListener {
            passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordShowIcon.isVisible = false
            passwordHideIcon.isVisible = true
        }

        passwordHideIcon.setOnClickListener {
            passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordShowIcon.isVisible = true
            passwordHideIcon.isVisible = false
        }




    }


}