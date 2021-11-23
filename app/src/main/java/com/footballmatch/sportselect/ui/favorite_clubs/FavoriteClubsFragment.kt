package com.footballmatch.sportselect.ui.favorite_clubs

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentFavoriteClubsBinding
import com.footballmatch.sportselect.model.FootballCommand
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import com.footballmatch.sportselect.ui.base.BaseFragment
import com.footballmatch.sportselect.ui.football_clubs.AdapterFootballClubs
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteClubsFragment :
    BaseFragment<FragmentFavoriteClubsBinding>(R.layout.fragment_favorite_clubs) {

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

        val footballCommandList = arrayListOf<FootballCommand>()
        val adapterFootballClubs = AdapterFavoriteClubs(::goToFullDescriptionClub)

        with(binding) {
            fun AssetManager.readFile(fileName: String) = open(fileName)
                .bufferedReader()
                .use { it.readText() }

            val json = context?.assets?.readFile("resp.json")
            val jsonObject = JSONArray(json)

            for (command in 0 until jsonObject.length()) {
                try {
                    val oneCommand: JSONObject = jsonObject.getJSONObject(command)
                    Log.e("PPP", preferenceStoreImpl.listFootballCommand.arrayListClub.toString())
                    preferenceStoreImpl.listFootballCommand.arrayListClub.forEach { nameClubs ->
                        if (nameClubs == oneCommand.getString("teamName")) {
                            footballCommandList.add(
                                FootballCommand(
                                    oneCommand.getString("teamName"),
                                    oneCommand.getString("teamCity"),
                                    oneCommand.getString("country"),
                                    oneCommand.getString("fullName"),
                                    oneCommand.getString("createdDate"),
                                    oneCommand.getString("stadion"),
                                    oneCommand.getString("site"),
                                    oneCommand.getString("description"),
                                    oneCommand.getString("logo")
                                )
                            )
                        }
                    }

                } catch (e: JSONException) {
                }
            }

            adapterFootballClubs.setData(footballCommandList)
            binding.favoriteClubsRecyclerView.layoutManager =
                LinearLayoutManager(contextTopActivity)
            binding.favoriteClubsRecyclerView.adapter = adapterFootballClubs

            favoriteClubsBackImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }
            favoriteClubsHomeImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }
        }
    }

    private fun goToFullDescriptionClub(name: String) {
        preferenceStoreImpl.teamName = name
        findNavController().navigate(R.id.action_favoriteClubsFragment_to_fullDescriptionClubFragment)
    }
}