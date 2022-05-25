package com.example.dipstore.home

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dipstore.R
import com.example.dipstore.databinding.LayoutSellBinding

class SellAdapter(private val tutorialData: ArrayList<SellData>) :
    RecyclerView.Adapter<SellAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(val binding: LayoutSellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = LayoutSellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(tutorialData[position]) {
                binding.tvProductName.text = title
                binding.tvBasePrice.text = basePrice
                if (basePrice == null) {
                    binding.tvBasePrice.visibility = View.INVISIBLE
                }
                else {
                    binding.tvBasePrice.visibility = View.VISIBLE
                }
                binding.tvBasePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.cvProduct.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java))
                }

                binding.tvFinalPrice.text = finalPrice
                binding.ivSellImage.setImageResource(image)
                if (likeStatus) {
                    binding.ivLike.setImageResource(R.drawable.like_filled)
                } else {
                    binding.ivLike.setImageResource(R.drawable.like)
                }
                binding.ivLike.setOnClickListener {
                    likeStatus = !likeStatus
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tutorialData.size
    }
}