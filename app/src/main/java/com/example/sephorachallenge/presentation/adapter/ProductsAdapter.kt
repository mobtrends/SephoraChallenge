package com.example.sephorachallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sephorachallenge.databinding.CellProductBinding
import com.example.sephorachallenge.domain.DisplayableProduct

class ProductsAdapter(
    private val displayableProduct: List<DisplayableProduct>,
    private val onProductClickListener: (Int, Int) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val binding = CellProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int = displayableProduct.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.brandNameTextView.text = displayableProduct[position].brandName
        holder.productNameTextView.text = displayableProduct[position].productName
        holder.productPrice.text = displayableProduct[position].price

        Glide.with(holder.itemView.context)
            .load(displayableProduct[position].image)
            .into(holder.productImageView)
        holder.productCardView.setOnClickListener {
            onProductClickListener(displayableProduct[position].id, position)
        }
    }

    inner class ProductsViewHolder(binding: CellProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productCardView: CardView = binding.productCardView
        val productImageView: ImageView = binding.productImageView
        val brandNameTextView: TextView = binding.brandNameTextView
        val productNameTextView: TextView = binding.productNameTextView
        val productPrice: TextView = binding.productPriceTextView
    }
}