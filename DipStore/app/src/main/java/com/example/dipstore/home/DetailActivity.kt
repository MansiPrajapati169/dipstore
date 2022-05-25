package com.example.dipstore.home

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.dipstore.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    private fun setData() {
        val mSpannableString = SpannableString(binding.tvSizeGuide.text)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        binding.apply {
            tvSizeGuide.text = mSpannableString
            ivFirst.setOnClickListener(this@DetailActivity)
            ivSecond.setOnClickListener(this@DetailActivity)
            ivThird.setOnClickListener(this@DetailActivity)
            ivFourth.setOnClickListener(this@DetailActivity)
            ivFifth.setOnClickListener(this@DetailActivity)
            customToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        supportActionBar?.hide()
    }

    override fun onClick(p0: View?) {
        p0?.let {
            val image = findViewById<ImageView>(it.id)
            val drawable = image.drawable
            binding.ivDetail.setImageDrawable(drawable)
        }
    }
}