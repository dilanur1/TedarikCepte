package com.example.tedarikcepte

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseHelper {
    fun getConnection(): Connection? {
        var connection: Connection? = null
        try {
            val url = "jdbc:postgresql://localhost:5433/TedarikCepte"
            val user = "postgres"
            val password = "deb.deb"
            connection = DriverManager.getConnection(url, user, password)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return connection
    }

    fun closeConnection(connection: Connection?) {
        try {
            connection?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}