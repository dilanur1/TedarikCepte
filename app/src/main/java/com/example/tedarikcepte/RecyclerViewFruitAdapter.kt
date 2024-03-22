package com.example.tedarikcepte

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class RecyclerViewFruitAdapter(

    private val getActivity: FruitActivitiy,
    private var fruitList: List<Fruit>,
    val context: Context) :

    RecyclerView.Adapter<RecyclerViewFruitAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    fun filterList(filteredList: ArrayList<Fruit>) {
        fruitList = filteredList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.meyveTitle.text = fruitList[position].category
        //holder.meyveImg.setImageResource(fruitList[position].image)
        holder.meyveFiyat.text = fruitList[position].price.toString() + " TL"

        val drawableResourceId: Int = holder.itemView.resources.getIdentifier(fruitList[position].imagePath, "drawable", holder.itemView.context.packageName);
        Glide.with(context).load(drawableResourceId).into(holder.meyveImg)


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val meyveTitle : TextView = itemView.findViewById(R.id.category)
        val meyveImg : ImageView = itemView.findViewById(R.id.productImg)
        val cardView : CardView = itemView.findViewById(R.id.productCardView)
        val meyveFiyat : TextView = itemView.findViewById(R.id.price)
        val addToCartBtn : Button = itemView.findViewById(R.id.addToCartBtn)

        init {
            addToCartBtn.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

        private fun addToCart(fruit: Fruit) {
/*            val intent = Intent(context, CartActivity::class.java)
            intent.putExtra("fruitCategory", meyveTitle.text.toString())
            intent.putExtra("fruitPrice", meyveFiyat.text.toString())

            context.startActivity(intent)*/
        }
    }


}