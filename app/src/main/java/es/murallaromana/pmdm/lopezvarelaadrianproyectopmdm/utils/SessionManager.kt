package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import android.content.Context
import android.content.SharedPreferences
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App

/**
 * Singleton in charge or managing the auth token through Shared Preferences.
 */
object SessionManager {
    private var prefs : SharedPreferences = App.GLB_STATE.context.getSharedPreferences(KEYS.TOKEN, Context.MODE_PRIVATE)

    /**
     * Save auth token in SharedPreferences
     */
    fun saveAuthToken(token: String) {
        with(prefs.edit()){
            putString(KEYS.TOKEN, token)
            commit()
        }
    }

    /**
     * Retrieve auth token from SharedPreferences
     */
    fun fetchAuthToken() : String? = prefs.getString(KEYS.TOKEN,null)

    /**
     * Remove the stored token from sharedPreferences
     */
    fun clearToken() {
        with(prefs.edit()) {
            remove(KEYS.TOKEN)
            commit()
        }
    }

}