package com.example.tedarikcepte

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class FruitActivitiy : AppCompatActivity() {

    //val progressBar: ProgressBar = findViewById(R.id.progressBar)
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewFruitAdapter: RecyclerViewFruitAdapter
    private var fruitList = mutableListOf<Fruit>()
    private lateinit var searchView: SearchView
    private lateinit var homeLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)

        recyclerView = findViewById(R.id.meyveRV)
        searchView = findViewById(R.id.searchView)
        val sessionManagement = SessionManagement(this)


        homeLayout = findViewById(R.id.homeLayout)
        homeLayout.setOnClickListener(View.OnClickListener {
            goToHomeActivity()
        })

        //fruitList = ArrayList()
        recyclerViewFruitAdapter = RecyclerViewFruitAdapter(this@FruitActivitiy, fruitList, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewFruitAdapter
        getFruits()

        recyclerViewFruitAdapter.setOnItemClickListener(object :
            RecyclerViewFruitAdapter.OnItemClickListener {
            val quantity = 8.0
            override fun onItemClick(position: Int) {
                val user = sessionManagement.getUser()
                val userId = user["user_id"] as Long
                showQuantityDialog(this@FruitActivitiy) { quantity ->

                    makeAddToCartRequest(fruitList[position], userId, quantity)
                }

            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })


    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Fruit> = ArrayList()

        for (item in fruitList) {
            if (item.category.lowercase().contains(text.lowercase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            recyclerViewFruitAdapter.filterList(filteredList)
        } else {
            recyclerViewFruitAdapter.filterList(filteredList)
        }
    }

    private fun goToHomeActivity() {
        val goToHomeActivity = Intent(this, MainActivity::class.java)
        startActivity(goToHomeActivity)
        finish()
    }

    private fun getFruits() {
        val category: String = "meyve"

        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.56.1:8080/api/v1/product?category=$category"

        val jsonArrayRequest = object : JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                //progressBar.visibility = View.GONE
                for (i in 0 until response.length()) {

                    try {
                        val jsonObject: JSONObject = response.getJSONObject(i)

                        addDataToList(jsonObject)


                    } catch (e: JSONException) {
                        e.printStackTrace()
                        System.out.println(e)
                    }

                }

            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                System.out.println(error)
                Toast.makeText(this, "Meyve bulunamadı!", Toast.LENGTH_LONG).show()
            }) {

        }

        requestQueue.add(jsonArrayRequest)
    }


    private fun addDataToList(jsonObject: JSONObject) {
        var fruit: Fruit? = null

        var id: Long = jsonObject.getLong("product_id")
        var name: String = jsonObject.getString("name")
        val price: Double = jsonObject.getDouble("price")

        if (name == "üzüm") {
            name = "uzum"
        }
        fruit = Fruit(id, name, name, price)
        fruitList.add(fruit)
        System.out.println("meyveler geldi")

        recyclerViewFruitAdapter!!.notifyDataSetChanged()
    }

    private fun makeAddToCartRequest(fruit: Fruit, user_id: Long, quantity: Double) {
        val requestQueue = Volley.newRequestQueue(this)
        val productId: Long = fruit.product_id
        val url = "http://192.168.56.1:8080/api/v1/cart/addProduct"


        val jsonBody = JSONObject()
        jsonBody.put("user_id", user_id)
        jsonBody.put("product_id", productId)
        jsonBody.put("quantity", quantity)
        val requestBody = jsonBody.toString()

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                if (response.equals("Ürün sepete eklendi.")) {
                    Toast.makeText(this, "Ürün sepete eklendi.", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Ürün sepete eklenemedi!", Toast.LENGTH_LONG).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray? {
                try {
                    return requestBody.toByteArray(Charsets.UTF_8)
                } catch (uee: UnsupportedEncodingException) {
                    return null
                }
            }
        }
        requestQueue.add(request)
    }


    fun showQuantityDialog(context: Context, onConfirm: (quantity: Double) -> Unit) {
        val editText = EditText(context)
        editText.hint = "Lütfen kaç kilogram istediğinizi giriniz."

        AlertDialog.Builder(context)
            .setTitle("Miktar Seçin")
            .setView(editText)
            .setPositiveButton("Tamam") { dialog, _ ->
                val quantityString = editText.text.toString()
                val quantity = if (quantityString.isNotEmpty()) quantityString.toDouble() else 0.0
                onConfirm(quantity)
                dialog.dismiss()
            }
            .setNegativeButton("İptal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}