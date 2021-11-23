package com.footballmatch.sportselect.ui.full_description_club

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import com.footballmatch.sportselect.ui.base.BaseFragment
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentFullDescriptionClubBinding
import com.footballmatch.sportselect.model.ArrayListFavoriteClub
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class FullDescriptionClubFragment :
    BaseFragment<FragmentFullDescriptionClubBinding>(R.layout.fragment_full_description_club) {

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

    @SuppressLint("SetTextI18n")
    private fun initView() {

        with(binding) {

            fun AssetManager.readFile(fileName: String) = open(fileName)
                .bufferedReader()
                .use { it.readText() }

            val playerListGoalkeepers = arrayListOf<String>()
            val playerListDefenders = arrayListOf<String>()
            val playerListMidfielders = arrayListOf<String>()
            val playerListAttackers = arrayListOf<String>()

            val json = context?.assets?.readFile("resp.json")
            val jsonObject = JSONArray(json)
            for (command in 0 until jsonObject.length()) {
                try {
                    val oneCommand: JSONObject = jsonObject.getJSONObject(command)

                    if (oneCommand.getString("teamName") == preferenceStoreImpl.teamName) {
                        fullDescriptionClubNameTxt.text = oneCommand.getString("teamName")
                        fullDescriptionClubCityTxt.text = oneCommand.getString("teamCity")

                        when (oneCommand.getString("teamName")) {
                            "Барселона" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(
                                R.drawable.ic_fc_barcelona
                            )
                            "Реал Мадрид" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(
                                R.drawable.ic_realmadrid
                            )
                            "Бавария" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(R.drawable.ic_fc_bayern_munchen)
                            "Валенсия" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(R.drawable.ic_valencia)
                            "Аякс" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(R.drawable.ic_amsterdam)
                            "Наполи" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(R.drawable.ic_napoli_logo)
                            "Ростов" -> fullDescriptionClubPhotoPlayersImg.setBackgroundResource(R.drawable.ic_rostov)
                        }


                        Picasso.get()
                            .load(oneCommand.getString("logo"))
                            .fit()
                            .into(fullDescriptionClubPhotoPlayersImg)

                        val players = oneCommand.getJSONArray("player")

                        for (player in 0 until players.length()) {
                            try {
                                val playerOne: JSONObject = players.getJSONObject(player)
                                if (playerOne.getString("amplua") == "Вратарь") {
                                    playerListGoalkeepers.add(
                                        "${playerOne.getString("number")} ${
                                            playerOne.getString(
                                                "name"
                                            )
                                        }\n"
                                    )
                                }

                                if (playerOne.getString("amplua") == "Защитник") {
                                    playerListDefenders.add(
                                        "${playerOne.getString("number")} ${
                                            playerOne.getString(
                                                "name"
                                            )
                                        }\n"
                                    )
                                }

                                if (playerOne.getString("amplua") == "Полузащитник") {
                                    playerListMidfielders.add(
                                        "${playerOne.getString("number")} ${
                                            playerOne.getString(
                                                "name"
                                            )
                                        }\n"
                                    )
                                }

                                if (playerOne.getString("amplua") == "Нападающий") {
                                    playerListAttackers.add(
                                        "${playerOne.getString("number")} ${
                                            playerOne.getString(
                                                "name"
                                            )
                                        }\n"
                                    )
                                }
                            } catch (e: JSONException) {
                            }
                        }

                        var strGoalkeepers = playerListGoalkeepers.toString()
                        strGoalkeepers.forEach { _ ->
                            strGoalkeepers = strGoalkeepers.replace('[', ' ')
                            strGoalkeepers = strGoalkeepers.replace(']', ' ')
                            strGoalkeepers = strGoalkeepers.replace(',', ' ')
                        }

                        var strDefenders = playerListDefenders.toString()
                        strDefenders.forEach { _ ->
                            strDefenders = strDefenders.replace('[', ' ')
                            strDefenders = strDefenders.replace(']', ' ')
                            strDefenders = strDefenders.replace(',', ' ')
                        }

                        var strMidfielders = playerListMidfielders.toString()
                        strMidfielders.forEach { _ ->
                            strMidfielders = strMidfielders.replace('[', ' ')
                            strMidfielders = strMidfielders.replace(']', ' ')
                            strMidfielders = strMidfielders.replace(',', ' ')
                        }

                        var strAttackers = playerListAttackers.toString()
                        strAttackers.forEach { _ ->
                            strAttackers = strAttackers.replace('[', ' ')
                            strAttackers = strAttackers.replace(']', ' ')
                            strAttackers = strAttackers.replace(',', ' ')
                        }


                        fullDescriptionClubPlayersTxt.text =
                            "${getString(R.string.goalkeepers)}\n\n${strGoalkeepers}\n${getString(R.string.defenders)}\n\n${strDefenders}\n${
                                getString(
                                    R.string.midfielders
                                )
                            }\n\n${strMidfielders}\n${getString(R.string.attackers)}\n\n${strAttackers}"

                        fullDescriptionClubDescriptionTxt.text =
                            "${getString(R.string.quick_reference)}\n\n${getString(R.string.city)}- ${
                                oneCommand.getString(
                                    "teamCity"
                                )
                            }\n${getString(R.string.country)}- ${
                                oneCommand.getString(
                                    "country"
                                )
                            }\n${getString(R.string.full_title)}- ${
                                oneCommand.getString(
                                    "fullName"
                                )
                            }\n${getString(R.string.foundation)}- ${
                                oneCommand.getString(
                                    "createdDate"
                                )
                            }\n${getString(R.string.stadium)}- ${
                                oneCommand.getString(
                                    "stadion"
                                )
                            }\n${getString(R.string.official_site)}- ${
                                oneCommand.getString(
                                    "site"
                                )
                            }\n\n${getString(R.string.team_history)}\n\n${oneCommand.getString("description")}"
                    }
                } catch (e: JSONException) {
                }
            }

            fullDescriptionClubAddImg.setOnClickListener {
                fullDescriptionClubAddTxt.text =
                    contextTopActivity.getString(R.string.added_favorites)
                val listFavoriteClub = arrayListOf<String>()
                preferenceStoreImpl.listFootballCommand.arrayListClub.forEach { command ->
                    listFavoriteClub.add(command)
                }
                listFavoriteClub.add(preferenceStoreImpl.teamName)
                val str = listFavoriteClub.distinct()
                listFavoriteClub.clear()
                str.forEach {
                    listFavoriteClub.add(it)
                }
                preferenceStoreImpl.listFootballCommand = ArrayListFavoriteClub(listFavoriteClub)
            }

            fullDescriptionClubBgCompositionImg.setOnClickListener {
                findNavController().navigate(R.id.action_fullDescriptionClubFragment_to_compositionFragment)
            }

            fullDescriptionClubBackImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }

            fullDescriptionClubHomeImgBtn.setOnClickListener {
                findNavController().navigate(R.id.action_fullDescriptionClubFragment_to_mainFragment)
            }
        }
    }
}
