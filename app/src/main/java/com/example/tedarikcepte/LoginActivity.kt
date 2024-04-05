package com.example.tedarikcepte

import android.Manifest.permission.SEND_SMS
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class LoginActivity: AppCompatActivity() {

    private val REQUEST_SMS = 123
    lateinit var sessionManagement: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerButton: TextView = findViewById(R.id.kayıtOlTxt)
        val loginButton: Button = findViewById(R.id.loginButton)
        val usernameField: EditText = findViewById(R.id.usernameField)
        val passwordField: EditText = findViewById(R.id.passwordField)
        val passwordShowIcon: ImageView = findViewById(R.id.passwordShowIcon)
        val passwordHideIcon: ImageView = findViewById(R.id.passwordHideIcon)
        val forgotPasswordTxt: TextView = findViewById(R.id.forgotPasswordTxt)


        sessionManagement = SessionManagement(this)



        fun goToSignUpActivity() {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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

            val jsonObjectRequest = object: JsonObjectRequest(Request.Method.POST, url, JSONObject(
                params as Map<String, String>
            ),
                Response.Listener<JSONObject> {response: JSONObject ->
                    try {

                        val user_id = response.getLong("user_id")
                        val first_name = response.get("first_name").toString()
                        val last_name = response.get("last_name").toString()
                        val firm = response.get("firm").toString()
                        val address = response.get("address").toString()
                        val phone = response.get("phone").toString()
                        val username = response.get("username").toString()
                        val password = response.get("password").toString()

                        val user = User(user_id, first_name, last_name, firm, address, phone, username, password)
                        sessionManagement.saveSession(user)
                        sessionManagement.saveUser(user)

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
            }) {}

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

        forgotPasswordTxt.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(SEND_SMS), REQUEST_SMS)
            } else {
                sendSMS()
            }
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_SMS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendSMS()
            }
        }
    }

    private fun sendSMS() {
        val phoneNumber = "05312512520"
        val message = "Mesajınız iletildi"

        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }

    fun goToMainActivity() {
        val goToMainActivity = Intent(this, MainActivity::class.java)
        goToMainActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(goToMainActivity)
        //onActivityResult()
        finish()
    }

    private fun checkSession() {

        val user_id: Long = sessionManagement.getSession()
        if(user_id != -1L) {
            goToMainActivity()
        }
    }
    override fun onStart() {
        super.onStart()

        checkSession()
    }

}