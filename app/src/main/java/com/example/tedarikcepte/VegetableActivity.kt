package com.example.tedarikcepte

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class VegetableActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewVegetableAdapter: RecyclerViewVegetableAdapter
    private var vegetableList = mutableListOf<Vegetable>()
    private lateinit var searchView: SearchView
    private lateinit var homepageLayout: LinearLayout
    private lateinit var cartBtn: ImageButton
    private lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetable)

        homepageLayout = findViewById(R.id.homepageLayout)
        homepageLayout.setOnClickListener(View.OnClickListener {
            goToHomeActivity()
        })

        cartBtn = findViewById(R.id.cartBtn)
        cartBtn.setOnClickListener(View.OnClickListener {
            goToCartActivity()
        })

        backBtn.setOnClickListener {
            goBack()
        }

        recyclerView = findViewById(R.id.vegetableRV)
        searchView = findViewById(R.id.searchView)
        val sessionManagement = SessionManagement(this)

        recyclerViewVegetableAdapter = RecyclerViewVegetableAdapter(this@VegetableActivity, vegetableList, this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewVegetableAdapter

        getVegetables()

        recyclerViewVegetableAdapter.setOnItemClickListener(object :
            RecyclerViewVegetableAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {
                val user = sessionManagement.getUser()
                val userId = user["user_id"] as Long
                showQuantityDialog(this@VegetableActivity) { quantity ->

                    makeAddToCartRequest(vegetableList[position], userId, quantity)
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

    private fun goBack() {
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Vegetable> = ArrayList()

        for (item in vegetableList) {
            if (item.category.lowercase().contains(text.lowercase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            recyclerViewVegetableAdapter.filterList(filteredList)
        } else {
            recyclerViewVegetableAdapter.filterList(filteredList)
        }
    }

    private fun goToHomeActivity() {
        val goToHomeActivity = Intent(this, MainActivity::class.java)
        startActivity(goToHomeActivity)
        finish()
    }

    private fun goToCartActivity() {
        val goToCartActivity = Intent(this, CartActivity::class.java)
        startActivity(goToCartActivity)
        finish()
    }

    private fun getVegetables() {

        val category: String = "sebze"

        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.56.1:8080/api/v1/product?category=$category"

        val jsonArrayRequest = object : JsonArrayRequest(
            Request.Method.GET, url, null,
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
                Toast.makeText(this, "Sebze bulunamadı!", Toast.LENGTH_LONG).show()
            }) {

        }

        requestQueue.add(jsonArrayRequest)
    }

    private fun addDataToList(jsonObject: JSONObject) {
        var vegetable: Vegetable? = null

        var id: Long = jsonObject.getLong("product_id")
        var name: String = jsonObject.getString("name")
        val price: Double = jsonObject.getDouble("price")

        if (name == "üzüm") {
            name = "uzum"
        }
        vegetable = Vegetable(id, name, name, price)
        vegetableList.add(vegetable)

        recyclerViewVegetableAdapter!!.notifyDataSetChanged()
    }

    private fun makeAddToCartRequest(vegetable: Vegetable, user_id: Long, quantity: Double) {
        val requestQueue = Volley.newRequestQueue(this)
        val productId: Long = vegetable.product_id
        val url = "http://192.168.56.1:8080/api/v1/cart/addProduct"


        val jsonBody = JSONObject()
        jsonBody.put("user_id", user_id)
        jsonBody.put("product_id", productId)
        jsonBody.put("quantity", quantity)
        val requestBody = jsonBody.toString()

        val request = object : StringRequest(
            Method.POST, url,
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