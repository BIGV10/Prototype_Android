package com.example.prototype

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.prototype.model.UserLogin

public class AuthHelper(context: Context) {

//    private val JWT_KEY_USERNAME = "username"
    private val JWT_KEY_USERNAME = "sub"
    private val JWT_KEY_ROLES = "roles"
    private val JWT_KEY_ENABLED = "enabled"

    private val PREFS = "prefs"
    private val PREF_TOKEN = "tokenJWT"

    private var mPrefs: SharedPreferences

    private var sInstance: AuthHelper? = null

    init {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        sInstance = this
    }

    fun setIdToken(token: UserLogin) {
        val editor = mPrefs.edit()
        editor.putString(PREF_TOKEN, token.accessToken)
        editor.apply()
    }

    fun getIdToken(): String? {
        return mPrefs.getString(PREF_TOKEN, null)
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

    fun getRoles(): List<String>? {
        return if (isLoggedIn()) {
            decodeRoles(getIdToken())?.asList()
        } else null
    }

    fun getEnabled(): Boolean {
        return if (isLoggedIn()) {
            decodeEnabled(getIdToken())
        } else false
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

    private fun decodeRoles(token: String?): Array<out String>? {
        val jwt = JWT(token!!)
        try {
            if (jwt.getClaim(JWT_KEY_ROLES) != null) {
                return jwt.getClaim(JWT_KEY_ROLES).asArray(String::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun decodeEnabled(token: String?): Boolean {
        val jwt = JWT(token!!)
        try {
            if (jwt.getClaim(JWT_KEY_ENABLED) != null) {
                return jwt.getClaim(JWT_KEY_ENABLED).asBoolean()!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun clear() {
        mPrefs.edit().clear().commit()
    }
}
