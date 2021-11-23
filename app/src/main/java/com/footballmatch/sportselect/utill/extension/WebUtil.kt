package com.footballmatch.sportselect.utill.extension

import androidx.annotation.StringRes
import com.footballmatch.sportselect.R

enum class CustomEvent(
    @StringRes val customEventTextId: Int
) {
    BALL(
        customEventTextId = R.string.custom_ball
    ),
    FIELD(
        customEventTextId = R.string.custom_game
    ),
    GOAL(
        customEventTextId = R.string.custom_leg
    ),
    SPORT(
    customEventTextId = R.string.custom_gates
    )
}