package com.example.qiyuanbao.memorymatch.game

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.qiyuanbao.memorymatch.R
import com.example.qiyuanbao.memorymatch.databinding.FragmentGameBinding
import com.example.qiyuanbao.memorymatch.extension.dpToPx
import com.example.qiyuanbao.memorymatch.adaptor.GameGridAdapter
import com.example.qiyuanbao.memorymatch.model.UserScore
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class GameFragment : Fragment() {
    companion object {
        private const val SPAN_COUNT = 5
        private const val DIALOG_TITLE_SP = 24f
        private const val DEFAULT_PAIRS = 2
        private const val DEFAULT_GRID_SIZE = 10
    }

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    private var gameGridAdapter: GameGridAdapter? = null

    private var gridSize = DEFAULT_GRID_SIZE
    private var matchPairs = DEFAULT_PAIRS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        setupRecyclerView()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // guarantees activity instance exists
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

        sharedPreferences.getString(
            getString(R.string.grid_size_preference_key),
            DEFAULT_GRID_SIZE.toString()
        )?.toInt()?.let {
            gridSize = it
        }

        sharedPreferences.getString(
            getString(R.string.game_pairs_preference_key),
            DEFAULT_PAIRS.toString()
        )?.toInt()?.let {
            matchPairs = it
        }

        activity?.application?.let {
            viewModel = ViewModelProvider(
                this,
                GameViewModelFactory(it, gridSize, matchPairs)
            ).get(GameViewModel::class.java)
        }
        
        onObserveProductImages()
        onObservePairsFound()
        onObserveUserScore()
        onObserveGameEndEvent()

        binding.shuffleButton.setOnClickListener {
            onShuffle()
        }

        super.onActivityCreated(savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.gamingGrid.layoutManager = GridLayoutManager(context, SPAN_COUNT)

        gameGridAdapter = GameGridAdapter()
        binding.gamingGrid.adapter = gameGridAdapter

        gameGridAdapter?.onCardClickListener = {
            viewModel.onCardClicked(it)
        }
    }

    private fun onObserveProductImages() {
        viewModel.productImages.observe(viewLifecycleOwner, Observer {
            gameGridAdapter?.submitNewList(it)
        })
    }

    private fun onObservePairsFound() {
        viewModel.pairsFound.observe(viewLifecycleOwner, Observer {
            updatePairsFound(it)
        })
    }

    private fun onObserveUserScore() {
        viewModel.userScore.observe(viewLifecycleOwner, Observer {
            updateUserScore(it)
        })
    }

    private fun onObserveGameEndEvent() {
        viewModel.gameEndEvent.observe(viewLifecycleOwner, Observer {
            if (it) gameFinished()
        })
    }

    private fun onShuffle() {
        viewModel.onShuffle()
    }

    private fun updatePairsFound(pairsFound: Int) {
        val text = "$pairsFound / $gridSize"
        binding.pairsFound.text = text
    }

    private fun updateUserScore(userScore: UserScore) {
        binding.gameScore.text = userScore.scores.toString()
    }

    private fun gameFinished() {
        createGameFinishedDialog()
    }

    private fun createGameFinishedDialog() {
        val title: TextView = TextView(context).apply {
            text = getString(R.string.game_finish_title)
            gravity = Gravity.CENTER
            setPadding(context.dpToPx(8))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, DIALOG_TITLE_SP)
            setTextColor(ContextCompat.getColor(context, R.color.polarisAccent))
            setTypeface(null, Typeface.BOLD)
        }

        val heroImage: ImageView = ImageView(context).apply {
            setPadding(context.dpToPx(16))
            setImageResource(R.drawable.you_won)
        }

        MaterialAlertDialogBuilder(context)
            .setCustomTitle(title)
            .setView(heroImage)
            .setNegativeButton(resources.getString(R.string.replay)) { _, _ ->
                viewModel.onPlayAgain()
                viewModel.onGameEndComplete()
            }
            .setPositiveButton(resources.getString(R.string.back_to_title)) { _, _ ->
                findNavController(this).navigate(R.id.action_gameFragment_to_homeFragment)
                viewModel.onGameEndComplete()
            }
            .show()
    }

}