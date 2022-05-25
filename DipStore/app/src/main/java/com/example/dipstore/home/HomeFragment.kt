package com.example.dipstore.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dipstore.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setData()
        return binding.root
    }

    private fun setData() {
        val sellData = SellData.getSellData(requireContext())
        val popularData = SellData.getPopularData(requireContext())
        val sellAdapter = SellAdapter(sellData)
        binding.rvSell.adapter = sellAdapter
        val popularAdapter = SellAdapter(popularData)
        binding.rvPopular.adapter = popularAdapter
    }
}