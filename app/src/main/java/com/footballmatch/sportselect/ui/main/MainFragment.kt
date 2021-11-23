package com.footballmatch.sportselect.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.footballmatch.sportselect.ui.base.BaseFragment
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        with(binding) {

            with(findNavController()) {

                mainFavoriteClubImgBtn.setOnClickListener {
                    navigate(R.id.action_mainFragment_to_favoriteClubsFragment)
                }

                mainFavoritePlayerImgBtn.setOnClickListener {
                    navigate(R.id.action_mainFragment_to_favoriteFootballPlayersFragment)
                }

                mainFootballClubImgBtn.setOnClickListener {
                    navigate(R.id.action_mainFragment_to_footballClubsFragment)
                }
            }
        }
    }
}