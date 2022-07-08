package com.example.dipstore.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.core.content.edit
import com.google.gson.Gson

class SharedPreferenceHelper(context: Context) {

    val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            "APP_DATA",
            Context.MODE_PRIVATE
        )
    }

    val gson by lazy { Gson() }

    fun <T> putPrefs(value: T?,key: String) {
        sharedPrefs.edit {
            when (value) {
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Parcelable -> putString(key, gson.toJson(value))
                else -> throw Throwable("no such type exist to put data")
            }
        }
    }

    inline fun <reified T : Any> getPrefs(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> sharedPrefs.getString(key, defaultValue as? String) as T?
            Int::class -> sharedPrefs.getInt(key, defaultValue as? Int ?: 0) as T?
            Boolean::class -> sharedPrefs.getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> sharedPrefs.getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> sharedPrefs.getLong(key, defaultValue as? Long ?: 0) as T?
            else ->  gson.fromJson(sharedPrefs.getString(key, "") ?: "", T::class.java)
        }
    }

    fun removeSharedPreference(key: String) {
        sharedPrefs.edit {
            remove(key)
            commit()
        }
    }
}