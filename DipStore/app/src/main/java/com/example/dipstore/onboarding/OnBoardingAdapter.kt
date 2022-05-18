package com.example.dipstore.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dipstore.databinding.LayoutOnboardingBinding

class OnBoardingAdapter(private val tutorialData: ArrayList<OnBoardingData>): RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutOnboardingBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(tutorialData[position]) {
                binding.tvTitle.text = title
                binding.tvDescription.text = description
            }
        }
    }

    override fun getItemCount(): Int {
        return tutorialData.size
    }
}