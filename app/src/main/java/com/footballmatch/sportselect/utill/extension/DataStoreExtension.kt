package com.footballmatch.sportselect.utill.extension

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.firstOrNull


suspend fun DataStore<Preferences>.putString(value: String, key: String) {
    val dataStoreKey = stringPreferencesKey(key)
    this.edit { data ->
        data[dataStoreKey] = value
    }
}

suspend fun DataStore<Preferences>.getString(key: String, defaultValue: String = ""): String {
    val dataStoreKey = stringPreferencesKey(key)
    val preferences = this.data.firstOrNull()
    return preferences?.get(dataStoreKey) ?: defaultValue
}

suspend fun DataStore<Preferences>.putInt(value: Int, key: String) {
    val dataStoreKey = intPreferencesKey(key)
    this.edit { data ->
        data[dataStoreKey] = value
    }
}

suspend fun DataStore<Preferences>.getInt(key: String, defaultValue: Int = 0): Int {
    val dataStoreKey = intPreferencesKey(key)
    val preferences = this.data.firstOrNull()
    return preferences?.get(dataStoreKey) ?: defaultValue
}

suspend fun DataStore<Preferences>.putBoolean(value: Boolean, key: String) {
    val dataStoreKey = booleanPreferencesKey(key)
    this.edit { data ->
        data[dataStoreKey] = value
    }
}

suspend fun DataStore<Preferences>.getBoolean(key: String, defaultValue: Boolean = false): Boolean {
    val dataStoreKey = booleanPreferencesKey(key)
    val preferences = this.data.firstOrNull()
    return preferences?.get(dataStoreKey) ?: defaultValue
}
