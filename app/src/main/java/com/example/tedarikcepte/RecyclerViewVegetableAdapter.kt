package com.example.tedarikcepte

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewVegetableAdapter(
    private val getActivity: VegetablesActivity,
    private val sebzeList: List<Vegetable>,
    val context: Context
) :

    RecyclerView.Adapter<RecyclerViewVegetableAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.`viewholder_list_product`, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sebzeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.sebzeTitle.text = sebzeList[position].category
       // holder.sebzeImg.setImageResource(sebzeList[position].image)
        holder.sebzeFiyat.text = sebzeList[position].price.toString()

        val drawableResourceId: Int = holder.itemView.resources.getIdentifier(sebzeList[position].imagePath, "drawable", holder.itemView.context.packageName);
        Glide.with(context).load(drawableResourceId).into(holder.sebzeImg)

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sebzeTitle : TextView = itemView.findViewById(R.id.category)
        val sebzeImg : ImageView = itemView.findViewById(R.id.productImg)
        val cardView : CardView = itemView.findViewById(R.id.productCardView)
        val sebzeFiyat : TextView = itemView.findViewById(R.id.price)
    }

}