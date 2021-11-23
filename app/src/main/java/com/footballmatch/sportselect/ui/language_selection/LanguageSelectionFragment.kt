package com.footballmatch.sportselect.ui.language_selection

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.footballmatch.sportselect.ui.base.BaseFragment
import com.footballmatch.sportselect.R
import com.footballmatch.sportselect.databinding.FragmentLanguageSelectionBinding
import com.footballmatch.sportselect.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class LanguageSelectionFragment :
    BaseFragment<FragmentLanguageSelectionBinding>(R.layout.fragment_language_selection) {

    private val contextTopActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        binding.languageSelectionRussianImgBtn.setOnClickListener {
            localization("ru")
            findNavController().navigate(R.id.action_languageSelectionFragment_to_mainFragment)
        }

        binding.languageSelectionEnglishImgBtn.setOnClickListener {
            localization("en")
            findNavController().navigate(R.id.action_languageSelectionFragment_to_mainFragment)
        }
    }

    private fun localization(language: String) {
        val res = contextTopActivity.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = Locale(language.toLowerCase(Locale.ROOT))
        res.updateConfiguration(conf, dm)
    }
}

