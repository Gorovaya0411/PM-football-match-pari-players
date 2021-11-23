package com.footballmatch.sportselect.ui.composition

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import com.footballmatch.sportselect.ui.base.BaseFragment
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentTeamCompositionBinding
import com.footballmatch.sportselect.model.ArrayListFavoritePlayer
import com.footballmatch.sportselect.model.FavoritePlayer
import com.footballmatch.sportselect.model.Player
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class TeamCompositionFragment :
    BaseFragment<FragmentTeamCompositionBinding>(R.layout.fragment_team_composition) {

    private val contextTopActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }
    var logoPLayer = ""
    var number = 0

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl

    @SuppressLint("SourceLockedOrientationActivity", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextTopActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
    }

    private fun initView() {

        val playerList = arrayListOf<Player>()

        with(binding) {
            fun AssetManager.readFile(fileName: String) = open(fileName)
                .bufferedReader()
                .use { it.readText() }

            val json = context?.assets?.readFile("resp.json")
            val jsonObject = JSONArray(json)

            for (command in 0 until jsonObject.length()) {
                try {
                    val oneCommand: JSONObject = jsonObject.getJSONObject(command)
                    if (oneCommand.getString("teamName") == preferenceStoreImpl.teamName) {
                        logoPLayer = oneCommand.getString("logo")
                        val players = oneCommand.getJSONArray("player")

                        for (player in 0 until players.length()) {
                            try {
                                val playerOne: JSONObject = players.getJSONObject(player)

                                playerList.add(
                                    Player(
                                        playerOne.getInt("number"),
                                        playerOne.getString("name"),
                                        playerOne.getInt("age"),
                                        playerOne.getInt("growth"),
                                        playerOne.getInt("weight"),
                                        playerOne.getString("amplua"),
                                        playerOne.getString("сitizenship"),
                                        playerOne.getString("bio")
                                    )
                                )
                                number = playerOne.getInt("number")
                            } catch (e: JSONException) {
                            }
                        }
                    }
                } catch (e: JSONException) {
                }
            }

            val adapterTeamComposition =
                AdapterTeamComposition(
                    ::goToFullBioFootballPlayer,
                    ::saveFavoritePlayer,
                    preferenceStoreImpl.teamName,
                    logoPLayer
                )

            adapterTeamComposition.setData(playerList)

            teamCompositionRecyclerView.layoutManager =
                LinearLayoutManager(contextTopActivity)
            teamCompositionRecyclerView.adapter = adapterTeamComposition

            teamCompositionBackImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }

            teamCompositionHomeImgBtn.setOnClickListener {
                contextTopActivity.navController.navigate(R.id.action_compositionFragment_to_mainFragment)
            }
        }
    }

    private fun goToFullBioFootballPlayer(name: String) {
        preferenceStoreImpl.footballPlayerName = name
        findNavController().navigate(R.id.action_compositionFragment_to_fullBiographyFootballPlayerFragment)
    }

    private fun saveFavoritePlayer(name: String) {
        val listFavoritePLayer = arrayListOf<FavoritePlayer>()
        preferenceStoreImpl.listFootballPlayers.listFavoritePlayer.forEach { player ->
            listFavoritePLayer.add(player)
        }
        listFavoritePLayer.add(
            FavoritePlayer(
                logoPLayer,
                preferenceStoreImpl.teamName,
                name,
                number
            )
        )
        val str = listFavoritePLayer.distinct()
        listFavoritePLayer.clear()
        str.forEach {
            listFavoritePLayer.add(it)
        }
        preferenceStoreImpl.listFootballPlayers = ArrayListFavoritePlayer(listFavoritePLayer)
    }
}