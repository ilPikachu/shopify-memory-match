package com.example.qiyuanbao.memorymatch.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.qiyuanbao.memorymatch.R
import com.example.qiyuanbao.memorymatch.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container, false)

        binding.playButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
        }

        binding.settingsButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        return binding.root
    }
}