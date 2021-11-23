package com.footballmatch.sportselect.ui.favorite_football_players

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentFavoriteFootballPlayersBinding
import com.footballmatch.sportselect.model.Player
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import com.footballmatch.sportselect.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFootballPlayersFragment :
    BaseFragment<FragmentFavoriteFootballPlayersBinding>(R.layout.fragment_favorite_football_players) {

    private val contextTopActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextTopActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
    }

    private fun initView() {
        val playerList = arrayListOf<Player>()

        with(binding) {

            val adapterTeamComposition =
                AdapterFavoriteFootballPlayers(
                    ::goToFullBioFootballPlayer
                )

            adapterTeamComposition.setData(preferenceStoreImpl.listFootballPlayers.listFavoritePlayer)

            favoritePlayersRecyclerView.layoutManager =
                LinearLayoutManager(contextTopActivity)
            favoritePlayersRecyclerView.adapter = adapterTeamComposition

            favoritePlayersBackImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }
            favoritePlayersHomeImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }
        }
    }

    private fun goToFullBioFootballPlayer(name: String) {
        preferenceStoreImpl.footballPlayerName = name
        findNavController().navigate(R.id.action_favoriteFootballPlayersFragment_to_fullBiographyFootballPlayerFragment)
    }
}