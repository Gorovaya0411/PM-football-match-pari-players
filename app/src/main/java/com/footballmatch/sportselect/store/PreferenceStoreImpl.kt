package com.footballmatch.sportselect.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.footballmatch.sportselect.model.ArrayListFavoriteClub
import com.footballmatch.sportselect.model.ArrayListFavoritePlayer
import com.footballmatch.sportselect.utill.extension.*
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PreferenceStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceStore {

    private val datastore: DataStore<Preferences> = context.dataStore

    override var listFootballPlayers: ArrayListFavoritePlayer
        get() = runBlocking {
            try {
                val gson = Gson().fromJson(
                    datastore.getString(KEY_LIST_FOOTBALL_PLAYER, ""),
                    ArrayListFavoritePlayer::class.java
                )
                return@runBlocking gson ?: ArrayListFavoritePlayer(ArrayList())
            } catch (e: Exception) {
                ArrayListFavoritePlayer(ArrayList())
            }
        }
        set(value) = runBlocking {
            datastore.putString(Gson().toJson(value) ?: "", KEY_LIST_FOOTBALL_PLAYER)
        }

    override var listFootballCommand: ArrayListFavoriteClub
        get() = runBlocking {
            try {
                val gson = Gson().fromJson(
                    datastore.getString(KEY_LIST_FOOTBALL_COMMAND, ""),
                    ArrayListFavoriteClub::class.java
                )
                return@runBlocking gson ?: ArrayListFavoriteClub(ArrayList())
            } catch (e: Exception) {
                ArrayListFavoriteClub(ArrayList())
            }
        }
        set(value) = runBlocking {
            datastore.putString(Gson().toJson(value) ?: "", KEY_LIST_FOOTBALL_COMMAND)
        }

    override var teamName: String
        get() = runBlocking { datastore.getString(KEY_TEAM_NAME) }
        set(value) = runBlocking { datastore.putString(value, KEY_TEAM_NAME) }

    override var footballPlayerName: String
        get() = runBlocking { datastore.getString(KEY_FOOTBALL_PLAYER_NAME) }
        set(value) = runBlocking { datastore.putString(value, KEY_FOOTBALL_PLAYER_NAME) }

    override var footballUri: String
        get() = runBlocking { datastore.getString(URI, "") }
        set(value) = runBlocking { datastore.putString(value, URI) }

    override var ballRun: Boolean
        get() = runBlocking { datastore.getBoolean(KEY_FIRST_RUN) }
        set(value) = runBlocking { datastore.putBoolean(value, KEY_FIRST_RUN) }

    override var reFootball: Int
        get() = runBlocking { datastore.getInt(KEY_RE_PIN, 0) }
        set(value) = runBlocking { datastore.putInt(value, KEY_RE_PIN) }

    override var uuIdFootball: String
        get() = runBlocking { datastore.getString(KEY_UU_ID, "") }
        set(value) = runBlocking { datastore.putString(value, KEY_UU_ID) }

    override var adIdFootball: String
        get() = runBlocking { datastore.getString(KEY_AD_ID, "") }
        set(value) = runBlocking { datastore.putString(value, KEY_AD_ID) }

    override var installationId: String
        get() = runBlocking { datastore.getString(KEY_INSTALLATION_ID, "") }
        set(value) = runBlocking { datastore.putString(value, KEY_INSTALLATION_ID) }

    override var deFootball: Int
        get() = runBlocking { datastore.getInt(KEY_DE_PIN, 0) }
        set(value) = runBlocking { datastore.putInt(value, KEY_DE_PIN) }

    companion object {
        const val KEY_LIST_FOOTBALL_PLAYER = "key_list_alarm_clock"
        const val KEY_LIST_FOOTBALL_COMMAND = "key_list_football_command"
        const val KEY_TEAM_NAME = "key_team_name"
        const val KEY_FOOTBALL_PLAYER_NAME = "key_football_player_name"
        private const val KEY_FIRST_RUN = "key first launch"
        private const val URI = "URI"
        const val KEY_INSTALLATION_ID = "installation_id"
        const val KEY_RE_PIN = "key re pin"
        private const val KEY_UU_ID = "uuid"
        const val KEY_DE_PIN = "key de pin"
        const val KEY_AD_ID = "ad_id"
    }
}