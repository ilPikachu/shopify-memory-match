package com.example.qiyuanbao.memorymatch.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qiyuanbao.memorymatch.databinding.GridViewItemBinding
import com.example.qiyuanbao.memorymatch.model.ProductImage

class GameGridAdapter() :
    RecyclerView.Adapter<GameGridAdapter.ProductImageViewHolder>() {

    private var productImages: List<ProductImage> = mutableListOf()

    var onCardClickListener: ((productImage: ProductImage) -> Unit)? = null

    class ProductImageViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productImage: ProductImage, onCardClickListener: ((productImage: ProductImage) -> Unit)?) {
            binding.productImage = productImage
            binding.cardview.setOnClickListener { onCardClickListener?.invoke(productImage) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val binding = GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProductImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        val productImage = productImages[position]
        holder.bind(productImage, onCardClickListener)
    }

    override fun getItemCount() =  productImages.size

    fun submitNewList(productImages: List<ProductImage>) {
        this.productImages = productImages
        notifyDataSetChanged()
    }

}