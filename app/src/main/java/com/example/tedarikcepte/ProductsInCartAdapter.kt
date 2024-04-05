package com.example.tedarikcepte

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsInCartAdapter(

    private val getActivity: CartActivity,
    private var productsInCartList: List<Product>,
    val context: Context
) :

    RecyclerView.Adapter<ProductsInCartAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productsInCartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val price = productsInCartList[position].price
        val quantity = productsInCartList[position].quantity as Double
        val sumOfPrice: Double = price*quantity

        holder.category.text = productsInCartList[position].category
        holder.price.text = sumOfPrice.toString() + " TL"
        holder.quantity.text = productsInCartList[position].quantity.toString() + " kg"

        val drawableResourceId: Int = holder.itemView.resources.getIdentifier(productsInCartList[position].imagePath, "drawable", holder.itemView.context.packageName);
        Glide.with(context).load(drawableResourceId).into(holder.img)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val img: ImageView = itemView.findViewById(R.id.productImg)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
    }
}