package com.footballmatch.sportselect.ui.full_biography_football_player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentFullBiographyFootballPlayerBinding
import com.footballmatch.sportselect.model.ArrayListFavoritePlayer
import com.footballmatch.sportselect.model.FavoritePlayer
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import com.footballmatch.sportselect.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class FullBiographyFootballPlayerFragment :
    BaseFragment<FragmentFullBiographyFootballPlayerBinding>(R.layout.fragment_full_biography_football_player) {

    private val contextTopActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl
    var logo = ""
    var number = 0

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextTopActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

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
                        val players = oneCommand.getJSONArray("player")

                        for (player in 0 until players.length()) {
                            try {
                                val playerOne: JSONObject = players.getJSONObject(player)

                                if (playerOne.getString("name") == preferenceStoreImpl.footballPlayerName) {
                                    when (oneCommand.getString("teamName")) {
                                        "Барселона" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_fc_barcelona
                                        )
                                        "Реал Мадрид" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_realmadrid
                                        )
                                        "Бавария" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_fc_bayern_munchen
                                        )
                                        "Валенсия" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_valencia
                                        )
                                        "Аякс" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_amsterdam
                                        )
                                        "Наполи" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_napoli_logo
                                        )
                                        "Ростов" -> fullBiographyFootballPlayerPhotoPlayersImg.setBackgroundResource(
                                            R.drawable.ic_rostov
                                        )
                                    }

                                    Picasso.get()
                                        .load(oneCommand.getString("logo"))
                                        .fit()
                                        .into(fullBiographyFootballPlayerPhotoPlayersImg)

                                    logo = oneCommand.getString("logo")
                                    number = playerOne.getInt("number")

                                    fullBiographyFootballPlayerNameTxt.text =
                                        playerOne.getString("name")

                                    fullBiographyFootballPlayerCityTxt.text =
                                        playerOne.getString("name")

                                    fullBiographyFootballPlayerRoleTxt.text =
                                        "${getString(R.string.role)} ${playerOne.getString("amplua")}"

                                    fullBiographyFootballPlayerNumberTxt.text =
                                        "${getString(R.string.number)} ${playerOne.getInt("number")}"

                                    fullBiographyFootballPlayerNationalTeamTxt.text =
                                        "${getString(R.string.national_team)} ${
                                            oneCommand.getString(
                                                "teamName"
                                            )
                                        }"

                                    fullBiographyFootballPlayerAgeTxt.text =
                                        "${getString(R.string.age)} ${
                                            playerOne.getInt("age")
                                        }"

                                    fullBiographyFootballPlayerWeightTxt.text =
                                        "${getString(R.string.weight)} ${
                                            playerOne.getInt("weight")
                                        }"

                                    fullBiographyFootballPlayerHeightTxt.text =
                                        "${getString(R.string.height)} ${
                                            playerOne.getInt("growth")
                                        }"

                                    fullBiographyFootballPlayerCitizenshipTxt.text =
                                        "${getString(R.string.citizenship)} ${
                                            playerOne.getString("сitizenship")
                                        }"

                                    fullBiographyFootballPlayerFullBiographyTxt.text = playerOne.getString("bio")
                                }
                            } catch (e: JSONException) {
                            }
                        }
                    }
                } catch (e: JSONException) {
                }
            }

            fullBiographyFootballPlayerAddPlayersImg.setOnClickListener {
                fullBiographyFootballPlayerAddPlayersTxt.text =
                    contextTopActivity.getString(R.string.added_favorites)

                try {
                    val listFavoritePLayer = arrayListOf<FavoritePlayer>()

                    preferenceStoreImpl.listFootballPlayers.listFavoritePlayer.forEach { player ->
                        listFavoritePLayer.add(player)
                    }
                    listFavoritePLayer.add(
                        FavoritePlayer(
                            logo,
                            preferenceStoreImpl.teamName,
                            preferenceStoreImpl.footballPlayerName,
                            number
                        )
                    )

                    val str = listFavoritePLayer.distinct()

                    listFavoritePLayer.clear()

                    str.forEach {
                        listFavoritePLayer.add(it)
                    }

                    preferenceStoreImpl.listFootballPlayers =
                        ArrayListFavoritePlayer(listFavoritePLayer)
                } catch (e: JSONException) {

                }
            }

            fullBiographyFootballPlayerBackImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }

            fullBiographyFootballPlayerHomeImgBtn.setOnClickListener {
                findNavController().navigate(R.id.action_fullBiographyFootballPlayerFragment_to_mainFragment)
            }
        }
    }
}