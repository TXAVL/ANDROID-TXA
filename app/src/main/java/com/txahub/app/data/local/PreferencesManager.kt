package com.txahub.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.txahub.app.utils.LocaleHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "txa_hub_prefs")

class PreferencesManager(private val context: Context) {
    
    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val IS_ADMIN_KEY = booleanPreferencesKey("is_admin")
        private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val CHANGELOG_VIEWED_VERSION_KEY = stringPreferencesKey("changelog_viewed_version")
    }
    
    val authToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[AUTH_TOKEN_KEY]
    }
    
    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_EMAIL_KEY]
    }
    
    val userName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME_KEY]
    }
    
    val userId: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }
    
    val isAdmin: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_ADMIN_KEY] ?: false
    }
    
    val rememberMe: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[REMEMBER_ME_KEY] ?: false
    }
    
    val language: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LANGUAGE_KEY] ?: LocaleHelper.LANGUAGE_AUTO
    }
    
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[AUTH_TOKEN_KEY] = token
        }
    }
    
    suspend fun saveUserInfo(email: String, name: String, userId: String, isAdmin: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL_KEY] = email
            prefs[USER_NAME_KEY] = name
            prefs[USER_ID_KEY] = userId
            prefs[IS_ADMIN_KEY] = isAdmin
        }
    }
    
    suspend fun setRememberMe(remember: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[REMEMBER_ME_KEY] = remember
        }
    }
    
    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
    
    suspend fun getAuthTokenSync(): String? {
        return context.dataStore.data.map { it[AUTH_TOKEN_KEY] }.first()
    }
    
    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language
        }
    }
    
    suspend fun getLanguageSync(): String {
        return context.dataStore.data.map { it[LANGUAGE_KEY] ?: LocaleHelper.LANGUAGE_AUTO }.first()
    }
    
    data class UserInfo(
        val email: String,
        val name: String,
        val userId: String,
        val isAdmin: Boolean
    )
    
    suspend fun getUserInfoSync(): UserInfo? {
        val prefs = context.dataStore.data.first()
        val email = prefs[USER_EMAIL_KEY] ?: return null
        val name = prefs[USER_NAME_KEY] ?: return null
        val userId = prefs[USER_ID_KEY] ?: return null
        val isAdmin = prefs[IS_ADMIN_KEY] ?: false
        return UserInfo(email, name, userId, isAdmin)
    }
    
    /**
     * Kiểm tra xem đã xem changelog cho version này chưa
     */
    suspend fun hasViewedChangelogForVersion(versionName: String): Boolean {
        val prefs = context.dataStore.data.first()
        val viewedVersion = prefs[CHANGELOG_VIEWED_VERSION_KEY]
        return viewedVersion == versionName
    }
    
    /**
     * Lưu đã xem changelog cho version này
     */
    suspend fun markChangelogViewedForVersion(versionName: String) {
        context.dataStore.edit { prefs ->
            prefs[CHANGELOG_VIEWED_VERSION_KEY] = versionName
        }
    }
    
    /**
     * Lấy version đã xem changelog (sync)
     */
    suspend fun getViewedChangelogVersionSync(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[CHANGELOG_VIEWED_VERSION_KEY]
    }
}


