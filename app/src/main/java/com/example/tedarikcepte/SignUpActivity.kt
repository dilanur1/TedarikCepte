package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tedarikcepte.databinding.ActivityMainBinding
import org.json.JSONArray

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val registerButton: Button = findViewById(R.id.registerButton)
        val firstnameField: EditText = findViewById(R.id.firstnameField)
        val lastnameField: EditText = findViewById(R.id.lastnameField)
        val firmField: EditText = findViewById(R.id.firmField)
        val addressField: EditText = findViewById(R.id.addressField)
        val phoneField: EditText = findViewById(R.id.phoneField)
        val usernameField: EditText = findViewById(R.id.usernameField)
        val passwordField: EditText = findViewById(R.id.passwordField)



        registerButton.setOnClickListener {
            val first_name = firstnameField.text.toString()
            var last_name = lastnameField.text.toString()
            var firm = firmField.text.toString()
            var address = addressField.text.toString()
            var phone = phoneField.text.toString()
            var username = usernameField.text.toString()
            var password = passwordField.text.toString()

            if (first_name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && firm.isNotEmpty()) {

                val requestQueue = Volley.newRequestQueue(this)
                val url = "http://192.168.56.1:8080/api/v1/user/register"

                val stringRequest = object : StringRequest(Request.Method.POST, url,
                    Response.Listener { response ->
                        if (response.equals("Kullanıcı başarıyla kaydedildi.")) {
                            firstnameField.setText(null)
                            lastnameField.setText(null)
                            firmField.setText(null)
                            addressField.setText(null)
                            phoneField.setText(null)
                            usernameField.setText(null)
                            passwordField.setText(null)
                            Toast.makeText(
                                this,
                                "Kullanıcı başarıyla kaydedildi.",
                                Toast.LENGTH_LONG
                            ).show()

                            switchToLoginPage()
                            finish()
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        System.out.println(error)
                        Toast.makeText(this, "Kullanıcı kaydedilemedi!", Toast.LENGTH_LONG).show()
                    }) {

                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params.put("first_name", first_name)
                        params.put("last_name", last_name)
                        params.put("firm", firm)
                        params.put("address", address)
                        params.put("phone", phone)
                        params.put("username", username)
                        params.put("password", password)

                        return params;
                    }
                }
                requestQueue.add(stringRequest)


            } else {
                Toast.makeText(
                    applicationContext,
                    "Boş bırakılan alanları doldurunuz.",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    fun switchToLoginPage() {
        val loginPage = Intent(this, LoginActivity::class.java)
        startActivity(loginPage)
    }
}
