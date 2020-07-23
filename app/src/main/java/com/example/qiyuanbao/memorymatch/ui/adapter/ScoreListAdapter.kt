package com.example.qiyuanbao.memorymatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qiyuanbao.memorymatch.databinding.UserScoreItemBinding
import com.example.qiyuanbao.memorymatch.data.model.UserScore

class ScoreListAdapter : RecyclerView.Adapter<ScoreListAdapter.UserScoreViewHolder>() {

    private var userScores: List<UserScore> = mutableListOf()

    class UserScoreViewHolder(private var binding: UserScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userScore: UserScore) {
            binding.score.text = userScore.scores.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserScoreViewHolder {
        val binding =
            UserScoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserScoreViewHolder(binding)
    }

    override fun getItemCount() = userScores.size

    override fun onBindViewHolder(holder: UserScoreViewHolder, position: Int) {
        val userScore = userScores[position]
        holder.bind(userScore)
    }

    fun submitNewList(userScores: List<UserScore>) {
        this.userScores = userScores
        notifyDataSetChanged()
    }

}