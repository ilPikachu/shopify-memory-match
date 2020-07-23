package com.example.qiyuanbao.memorymatch.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.qiyuanbao.memorymatch.R
import com.example.qiyuanbao.memorymatch.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.playButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
        }

        binding.settingsButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.userScoreButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_scoreFragment)
        }

        mediaPlayer = MediaPlayer.create(context, R.raw.music)
        setEasterEggMusicListener()

        return binding.root
    }

    // 千と千寻の神隠し_いつも何度でも
    private fun setEasterEggMusicListener() {
        binding.logo.setOnClickListener {
            Toast.makeText(
                context,
                "You discovered an easter egg, click again to pause/play",
                Toast.LENGTH_SHORT
            ).show()

            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
            }
        }
    }

    override fun onPause() {
        mediaPlayer?.pause()
        super.onPause()
    }
}