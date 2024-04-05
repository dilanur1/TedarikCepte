package com.example.tedarikcepte

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewVegetableAdapter(
    private val getActivity: VegetableActivity,
    private var vegetableList: List<Vegetable>,
    val context: Context
) :

    RecyclerView.Adapter<RecyclerViewVegetableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_list_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vegetableList.size
    }

    fun filterList(filteredList: ArrayList<Vegetable>) {
        vegetableList = filteredList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = vegetableList[position].category
        holder.price.text = vegetableList[position].price.toString() + " TL/kg"

        val drawableResourceId: Int = holder.itemView.resources.getIdentifier(
            vegetableList[position].imagePath,
            "drawable",
            holder.itemView.context.packageName
        );
        Glide.with(context).load(drawableResourceId).into(holder.img)

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.category)
        val img: ImageView = itemView.findViewById(R.id.productImg)
        val cardView: CardView = itemView.findViewById(R.id.productCardView)
        val price: TextView = itemView.findViewById(R.id.price)
        val addToCartBtn: Button = itemView.findViewById(R.id.addToCartBtn)

        init {
            addToCartBtn.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}