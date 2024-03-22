package com.example.tedarikcepte

import android.content.Context
import android.content.SharedPreferences

class SessionManagement(val context: Context) {
    private val SHARED_PREF_NAME: String = "session"
    private val SESSION_KEY: String = "session_user"
    private val KEY_USER_ID = "user_id"
    private val KEY_FIRST_NAME = "first_name"
    private val KEY_LAST_NAME = "last_name"
    private val KEY_FIRM = "firm"
    private val KEY_ADDRESS = "address"
    private val KEY_PHONE = "phone"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveSession(user: User) {
        val user_id = user.user_id
        editor.putLong(SESSION_KEY, user_id).commit()
    }

    fun getSession(): Long {
        return sharedPreferences.getLong(SESSION_KEY, -1)
    }

    fun removeSession() {
        editor.putLong(SESSION_KEY, -1).commit()
    }

    fun saveUser(user: User) {
        editor.putLong(KEY_USER_ID, user.user_id)
        editor.putString(KEY_FIRST_NAME, user.first_name)
        editor.putString(KEY_LAST_NAME, user.last_name)
        editor.putString(KEY_FIRM, user.firm)
        editor.putString(KEY_ADDRESS, user.address)
        editor.putString(KEY_PHONE, user.phone)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_PASSWORD, user.password)
        editor.apply()
    }

    fun getUser(): HashMap<String, Any> {
        val user = HashMap<String, Any>()
        user[KEY_USER_ID] = sharedPreferences.getLong(KEY_USER_ID, 0) ?: ""
        user[KEY_FIRST_NAME] = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
        user[KEY_LAST_NAME] = sharedPreferences.getString(KEY_LAST_NAME, "") ?: ""
        user[KEY_FIRM] = sharedPreferences.getString(KEY_FIRM, "") ?: ""
        user[KEY_ADDRESS] = sharedPreferences.getString(KEY_ADDRESS, "") ?: ""
        user[KEY_PHONE] = sharedPreferences.getString(KEY_PHONE, "") ?: ""
        user[KEY_USERNAME] = sharedPreferences.getString(KEY_USERNAME, "") ?: ""
        user[KEY_PASSWORD] = sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
        return user
    }


    fun logout() {
        editor.clear()
        editor.apply()
    }
}