package com.javaindo.lautnusantara.utility

import android.content.SharedPreferences
import javax.inject.Inject


class  PrefHelper @Inject constructor(private val sharedPreferences : SharedPreferences) {
    
    fun setIntToShared(enumEntry: String, value: Int) {
        try {
            sharedPreferences!!.edit().putInt(enumEntry, value).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun getIntFromShared(enumEntry: String): Int {
        return try {
            sharedPreferences!!.getInt(enumEntry, 0)
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            0
        }
    }

    fun setStringToShared(enumEntry: String, value: String?) {
        try {
            sharedPreferences!!.edit().putString(enumEntry, value).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun getStringFromShared(enumEntry: String): String? {
        return try {
            sharedPreferences!!.getString(enumEntry, "")
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            "null"
        }
    }

    fun setBooleanToShared(enumEntry: String, value: Boolean) {
        try {
            sharedPreferences!!.edit().putBoolean(enumEntry, value).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun getBoolFromShared(enumEntry: String): Boolean {
        return try {
            sharedPreferences!!.getBoolean(enumEntry, false)
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            false
        }
    }

    fun setStringSetToShared(enumEntry: String, value: Set<String?>?) {
        try {
            sharedPreferences!!.edit().putStringSet(enumEntry, value).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun getStringSetFromShared(enumEntry: String): Set<String>? {
        val emptySet: Set<String> = HashSet()
        return try {
            sharedPreferences!!.getStringSet(enumEntry, emptySet)
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            emptySet
        }
    }

    fun setFloatToShared(enumEntry: String, value: Float) {
        try {
            sharedPreferences!!.edit().putFloat(enumEntry, value).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun getFloatFromShared(enumEntry: String): Float {
        return try {
            sharedPreferences!!.getFloat(enumEntry, 0.0f)
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            0.0f
        }
    }

    fun setLongToShared(enumEntry: String, value: Long) {
        try {
            sharedPreferences!!.edit().putLong(enumEntry, value).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun getLongFromShared(enumEntry: String): Long {
        return try {
            sharedPreferences!!.getLong(enumEntry, 0)
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            0
        }
    }

    fun contain(enumEntry: String): Boolean {
        return try {
            sharedPreferences!!.contains(enumEntry)
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
            false
        }
    }

    fun remove(enumEntry: String) {
        try {
            sharedPreferences!!.edit().remove(enumEntry).apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }

    fun clear() {
        try {
            sharedPreferences!!.edit().clear().apply()
        } catch (exception: ExceptionInInitializerError) {
            exception.printStackTrace()
        }
    }


}