package com.footballmatch.sportselect.model

data class FootballCommand(
    val teamName: String,
    val teamCity: String,
    val country: String,
    val fullName: String,
    val createdDate: String,
    val stadion: String,
    val site: String,
    val description: String,
    val logo: String
)