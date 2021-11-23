package com.footballmatch.sportselect.store

import com.footballmatch.sportselect.model.ArrayListFavoriteClub
import com.footballmatch.sportselect.model.ArrayListFavoritePlayer

interface PreferenceStore {
    var teamName: String
    var footballPlayerName: String
    var listFootballPlayers: ArrayListFavoritePlayer
    var listFootballCommand: ArrayListFavoriteClub
    var ballRun: Boolean
    var footballUri: String
    var reFootball: Int
    var uuIdFootball: String
    var deFootball: Int
    var adIdFootball: String
    var installationId: String
}