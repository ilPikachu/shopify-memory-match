package com.example.qiyuanbao.memorymatch.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiyuanbao.memorymatch.R
import com.example.qiyuanbao.memorymatch.ui.adapter.ScoreListAdapter
import com.example.qiyuanbao.memorymatch.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private lateinit var viewModel: ScoreViewModel

    private var scoreListAdapter: ScoreListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)

        setupRecyclerView()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        activity?.application?.let {
            viewModel = ViewModelProvider(
                this,
                ScoreViewModelFactory(it)
            ).get(ScoreViewModel::class.java)
        }

        binding.lifecycleOwner = viewLifecycleOwner

        onObserveUserScore()

        super.onActivityCreated(savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.scoreList.layoutManager = LinearLayoutManager(context)

        scoreListAdapter = ScoreListAdapter()
        binding.scoreList.adapter = scoreListAdapter
    }

    private fun onObserveUserScore() {
        viewModel.userScores.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.noScoreText.visibility = View.GONE
                scoreListAdapter?.submitNewList(it)

            } else {
                binding.noScoreText.visibility = View.VISIBLE
            }
        })
    }

}