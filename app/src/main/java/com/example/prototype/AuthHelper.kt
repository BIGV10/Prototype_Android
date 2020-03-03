package com.example.prototype

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.auth0.android.jwt.JWT
import com.example.prototype.model.UserLogin

public class AuthHelper(context: Context) {

    private val JWT_KEY_USERNAME = "username"

    private val PREFS = "prefs"
    private val PREF_TOKEN = "tokenJWT"

    private lateinit var mPrefs: SharedPreferences

    private var sInstance: AuthHelper? = null

    init {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        sInstance = this
    }

    fun setIdToken(token: UserLogin) {
        val editor = mPrefs!!.edit()
        editor.putString(PREF_TOKEN, token.accessToken)
        editor.apply()
    }

    fun getIdToken(): String? {
        return mPrefs!!.getString(PREF_TOKEN, null)
    }

    fun isLoggedIn(): Boolean {
        val token = getIdToken()
        return token != null
    }

    fun getUsername(): String? {
        return if (isLoggedIn()) {
            decodeUsername(getIdToken())
        } else null
    }


    private fun decodeUsername(token: String?): String? {
        val jwt = JWT(token!!)
        try {
            if (jwt.getClaim(JWT_KEY_USERNAME) != null) {
                return jwt.getClaim(JWT_KEY_USERNAME).asString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun clear() {
        mPrefs!!.edit().clear().commit()
    }
}
