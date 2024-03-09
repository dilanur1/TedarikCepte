package com.example.tedarikcepte

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale


class FruitActivitiy: AppCompatActivity() {

    //val progressBar: ProgressBar = findViewById(R.id.progressBar)
    private  lateinit var recyclerView: RecyclerView
    private  lateinit var recyclerViewFruitAdapter: RecyclerViewFruitAdapter
    private var fruitList = mutableListOf<Fruit>()
    private lateinit var searchView: SearchView
    private lateinit var homeLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)

        recyclerView = findViewById(R.id.meyveRV)
        searchView = findViewById(R.id.searchView)


        homeLayout= findViewById(R.id.homeLayout)
        homeLayout.setOnClickListener(View.OnClickListener {
            goToHomeActivity()
        })

        //fruitList = ArrayList()
        recyclerViewFruitAdapter = RecyclerViewFruitAdapter(this@FruitActivitiy, fruitList, this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewFruitAdapter
        getFruits()

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

        val  requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.56.1:8080/api/v1/fruit"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> {response ->
                //progressBar.visibility = View.GONE
                for (i in 0 until response.length()) {

                    try {
                        val jsonObject: JSONObject = response.getJSONObject(i)

                        addDataToList(jsonObject)


                    }catch (e: JSONException) {
                        e.printStackTrace()
                        System.out.println(e)
                    }

                }

            },
            Response.ErrorListener {error ->
                error.printStackTrace()
                System.out.println(error)
                Toast.makeText(this, "Meyve bulunamadı!", Toast.LENGTH_LONG).show()
            })
        requestQueue.add(jsonArrayRequest)
    }


    private fun addDataToList(jsonObject: JSONObject) {
        var fruit: Fruit? = null

        var category: String = jsonObject.getString("category")
        val price: Double = jsonObject.getDouble("price")
        val number: Int = jsonObject.getInt("number")

        if(category == "üzüm") {category = "uzum"}
        fruit = Fruit(category, category, price, number)
        fruitList.add(fruit)

        recyclerViewFruitAdapter!!.notifyDataSetChanged()
    }
}