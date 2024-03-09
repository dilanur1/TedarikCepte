package com.example.tedarikcepte

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VegetablesActivity: AppCompatActivity() {
    private  var recyclerView: RecyclerView? = null
    private  var recyclerViewVegetableAdapter: RecyclerViewVegetableAdapter? = null
    private var sebzeList = mutableListOf<Vegetable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetable)

        val anasayfaLayout: LinearLayout =findViewById(R.id.anasayfaLayout)
        anasayfaLayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        sebzeList = ArrayList()

        recyclerView = findViewById<View>(R.id.sebzeRV) as RecyclerView
        recyclerViewVegetableAdapter = RecyclerViewVegetableAdapter(this@VegetablesActivity, sebzeList, this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewVegetableAdapter

        prepareSebzeListData()


    }

    private fun prepareSebzeListData() {
        //
        //db den çek

/*        var sebze = Vegetable("Marul", R.drawable.marul, 10)
        sebzeList.add(sebze)

        sebze = Vegetable("Domates", R.drawable.domates, 20)
        sebzeList.add(sebze)
        sebze = Vegetable("Patlıcan", R.drawable.patlican, 3)
        sebzeList.add(sebze)
        sebze = Vegetable("Salatalık", R.drawable.salatalik, 4)
        sebzeList.add(sebze)
        sebze = Vegetable("Çarliston Biber", R.drawable.carliston_biber, 13)
        sebzeList.add(sebze)

        recyclerViewVegetableAdapter!!.notifyDataSetChanged()*/


    }


}