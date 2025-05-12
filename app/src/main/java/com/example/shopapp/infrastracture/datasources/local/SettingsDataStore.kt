package com.example.shopapp.infrastracture.datasources.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

object SettingsDataStore {
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    private val ROLE_KEY = stringPreferencesKey("user_role")

    // ✅ Retrieve Token
    fun tokenFlow(context: Context): Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[TOKEN_KEY] }

    // ✅ Retrieve User Role
    fun userRoleFlow(context: Context): Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[ROLE_KEY] }

    // ✅ Save Token
    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { prefs -> prefs[TOKEN_KEY] = token }
        Log.d("SettingsDataStore", "✅ Token saved: $token")
    }

    // ✅ Save User Role
    suspend fun saveUserRole(context: Context, role: String) {
        context.dataStore.edit { prefs -> prefs[ROLE_KEY] = role }
        Log.d("SettingsDataStore", "✅ User Role saved: $role")
    }

    // ✅ Clear Token & Role on Logout
    suspend fun clearToken(context: Context) {
        context.dataStore.edit { prefs -> prefs.remove(TOKEN_KEY) }
        Log.d("SettingsDataStore", "❌ Token cleared")
    }

    suspend fun clearUserRole(context: Context) {
        context.dataStore.edit { prefs -> prefs.remove(ROLE_KEY) }
        Log.d("SettingsDataStore", "❌ User Role cleared")
    }
}