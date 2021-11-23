package com.footballmatch.sportselect.ui.splash

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentSplashBinding
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import com.footballmatch.sportselect.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val mainActivityContext: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityContext.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}