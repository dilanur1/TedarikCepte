package com.example.tedarikcepte

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
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

class CartActivity : AppCompatActivity() {
    private lateinit var backBtn: ImageView
    private lateinit var recyclerView: RecyclerView
    private var productsInCartList = mutableListOf<Product>()
    private lateinit var productsInCartAdapter: ProductsInCartAdapter
    var sum: Double = 0.0
    private lateinit var emptyCartLayout: ConstraintLayout
    private lateinit var notEmptySV: ScrollView
    private lateinit var backShoppingBtn: Button
    private lateinit var giveOrderBtn: Button
    private lateinit var paymentRadioBtn: RadioGroup
    private lateinit var totalPrice: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        backBtn = findViewById(R.id.backBtn)
        recyclerView = findViewById(R.id.cardView)
        emptyCartLayout = findViewById(R.id.emptyCartLayout)
        notEmptySV = findViewById(R.id.notEmptySV)
        backShoppingBtn = findViewById(R.id.backShoppingBtn)
        giveOrderBtn = findViewById(R.id.giveOrderBtn)
        paymentRadioBtn = findViewById(R.id.paymentRadioBtn)
        totalPrice = findViewById(R.id.totalPrice)

        backShoppingBtn.setOnClickListener {
            goToHomeActivity()
        }

        giveOrderBtn.setOnClickListener {
            if (paymentRadioBtn.checkedRadioButtonId == -1) {
                giveOrderBtn.isClickable = false
                Toast.makeText(this, "Lütfen ödeme yöntemi seçin.", Toast.LENGTH_SHORT).show()
            } else {
                val selectedPaymentMethodId = paymentRadioBtn.checkedRadioButtonId
                val selectedPaymentMethod = findViewById<RadioButton>(selectedPaymentMethodId)
                val selectedOptionTxt = selectedPaymentMethod.text
                val totalPriceDouble = totalPrice.text.split(" ").first()
                giveOrder(productsInCartList, selectedOptionTxt, totalPriceDouble.toDouble())
            }

        }

        val sessionManagement = SessionManagement(this)
        val user = sessionManagement.getUser()
        val userId = user["user_id"] as Long
        goBack()

        val category = intent.getStringExtra("fruitCategory")
        val price = intent.getStringExtra("fruitPrice")

        productsInCartAdapter = ProductsInCartAdapter(this@CartActivity, productsInCartList, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = productsInCartAdapter
        getProductsInCart(userId)

        productsInCartAdapter.setOnItemClickListener(object :
            ProductsInCartAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val user = sessionManagement.getUser()
                val userId = user["user_id"] as Long
                showQuantityDialog(this@CartActivity) { quantity ->
                }

            }
        })

    }

    private fun giveOrder(productsInCartList: MutableList<Product>, selectedOptionTxt: CharSequence?, toDouble: Double) {

    }

    private fun goToHomeActivity() {
        val goToHomeActivity = Intent(this, MainActivity::class.java)
        startActivity(goToHomeActivity)
        finish()
    }


    private fun getProductsInCart(userId: Long) {

        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.56.1:8080/api/v1/cart?user_id=$userId"

        val jsonArrayRequest = object : JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                notEmptySV.visibility = View.VISIBLE
                emptyCartLayout.visibility = View.GONE
                //progressBar.visibility = View.GONE
                for (i in 0 until response.length()) {

                    try {
                        val jsonObject: JSONObject = response.getJSONObject(i)
                        addDataToList(jsonObject, userId)


                    } catch (e: JSONException) {
                        e.printStackTrace()
                        System.out.println(e)
                    }

                }

            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                notEmptySV.visibility = View.GONE
                emptyCartLayout.visibility = View.VISIBLE

            }) {

        }

        requestQueue.add(jsonArrayRequest)

    }

    private fun addDataToList(jsonObject: JSONObject, userId: Long) {


        var productId: Long = jsonObject.getLong("product_id")
        var name: String = jsonObject.getString("name")
        val price: Double = jsonObject.getDouble("price")

        val requestQueue = Volley.newRequestQueue(this)
        val url =
            "http://192.168.56.1:8080/api/v1/cart_product/quantity?user_id=$userId&product_id=$productId"

        val stringRequest = object : StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                //progressBar.visibility = View.GONE
                val quantity = response.toDouble()

                val product = Product(productId, name, name, price, quantity)
                calculateTotalPrice(price, quantity)
                productsInCartList.add(product)

                productsInCartAdapter!!.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                System.out.println(error)
                Toast.makeText(this, "Sepette ürün bulunamadı!", Toast.LENGTH_LONG).show()
            }) {

        }

        requestQueue.add(stringRequest)

    }

    private fun calculateTotalPrice(price: Double, quantity: Double) {
        sum = sum + (price * quantity)
        totalPrice.text = sum.toString() + " TL"
    }


    private fun goBack() {
        backBtn.setOnClickListener {
            finish()
        }
    }

    fun showQuantityDialog(context: Context, onConfirm: (quantity: Double) -> Unit) {
        val editText = EditText(context)
        editText.hint = "Lütfen kaç kilogram istediğinizi giriniz."

        AlertDialog.Builder(context)
            .setTitle("Miktarı güncelleyin")
            .setView(editText)
            .setPositiveButton("Güncelle") { dialog, _ ->
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


































