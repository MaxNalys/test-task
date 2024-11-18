package com.example.testtask.utils

import android.content.Context
import com.example.testtask.data.model.CharacterModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveCharacters(characters: List<CharacterModel>) {
        val gson = Gson()
        val json = gson.toJson(characters)
        sharedPreferences.edit().putString("characters", json).apply()
    }

    fun getCharacters(): List<CharacterModel> {
        val gson = Gson()
        val json = sharedPreferences.getString("characters", null) ?: return emptyList()
        val type = object : TypeToken<List<CharacterModel>>() {}.type
        return gson.fromJson(json, type)
    }
}