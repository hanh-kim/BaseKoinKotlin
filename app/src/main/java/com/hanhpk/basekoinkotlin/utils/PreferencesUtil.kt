package com.hanhpk.basekoinkotlin.utils

import android.content.Context
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hanhpk.basekoinkotlin.AndroidApplication
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import java.lang.reflect.Type

object PreferencesUtil {
     val preferences = AndroidApplication.mInstance
        .getSharedPreferences(
            AndroidApplication.mInstance.getString(R.string.preference_name),
            Context.MODE_PRIVATE)
    private val preferencesEdit = preferences.edit()



    private const val PREF_FIRST_TIME_USER_SETTINGS_PREFECTURE = "PREF_FIRST_TIME_USER_SETTINGS_PREFECTURE"
    private const val PREF_FIRST_TIME_USER_SETTINGS_AREA = "PREF_FIRST_TIME_USER_SETTINGS_AREA"

    private const val PREF_KEY_SAVE_USER_POST = "PREF_KEY_SAVE_USER_POST"


    fun deleteCache(){
        preferencesEdit.clear().commit()
    }

    //user settings
    var firstTimeUserSettingsPrefecture: String?
        get(){
            return preferences.getString(PREF_FIRST_TIME_USER_SETTINGS_PREFECTURE, null)
        }
        set(value){
            preferencesEdit.putString(PREF_FIRST_TIME_USER_SETTINGS_PREFECTURE, value).apply()
        }

    var firstTimeUserSettingsArea: String?
        get(){
            return preferences.getString(PREF_FIRST_TIME_USER_SETTINGS_AREA, null)
        }
        set(value){
            return preferencesEdit.putString(PREF_FIRST_TIME_USER_SETTINGS_AREA, value).apply()
        }


    //saved list model
    var savedUserPost : List<UserPostResponse>
        get() {
            val json = preferences.getString(PREF_KEY_SAVE_USER_POST, null) ?: return arrayListOf()
            val type : Type = object : TypeToken<List<UserPostResponse>>(){}.type
            return Gson().fromJson(json,type)
        }
        set(value) {
            preferencesEdit.putString(PREF_KEY_SAVE_USER_POST, Gson().toJson(value)).apply()
        }

}