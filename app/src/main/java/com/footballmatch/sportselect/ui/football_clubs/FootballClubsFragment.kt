package com.footballmatch.sportselect.ui.football_clubs

import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentFootballClubsBinding
import com.footballmatch.sportselect.model.FootballCommand
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import com.footballmatch.sportselect.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject
import org.json.JSONArray
import org.json.JSONException

@AndroidEntryPoint
class FootballClubsFragment :
    BaseFragment<FragmentFootballClubsBinding>(R.layout.fragment_football_clubs) {

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl

    private val contextTopActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }


    private fun initView() {
        val footballCommandList = arrayListOf<FootballCommand>()
        val adapterFootballClubs = AdapterFootballClubs(::goToFullDescriptionClub)

        with(binding) {
            fun AssetManager.readFile(fileName: String) = open(fileName)
                .bufferedReader()
                .use { it.readText() }

            val json = context?.assets?.readFile("resp.json")
            val jsonObject = JSONArray(json)

            for (command in 0 until jsonObject.length()) {
                try {
                    val oneCommand: JSONObject = jsonObject.getJSONObject(command)

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
                } catch (e: JSONException) {
                }
            }

            adapterFootballClubs.setData(footballCommandList)
            binding.footballClubsRecyclerView.layoutManager =
                LinearLayoutManager(contextTopActivity)
            binding.footballClubsRecyclerView.adapter = adapterFootballClubs

            footballClubsBackImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }

            footballClubsHomeImgBtn.setOnClickListener {
                contextTopActivity.onBackPressed()
            }
        }
    }

    private fun goToFullDescriptionClub(name: String) {
        preferenceStoreImpl.teamName = name
        findNavController().navigate(R.id.action_footballClubsFragment_to_fullDescriptionClubFragment)
    }
}