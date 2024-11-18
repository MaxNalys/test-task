package com.example.testtask.utils

import android.content.Context
import android.util.Log
import com.example.testtask.data.model.CharacterModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun getCharacters(): List<CharacterModel> {
        val charactersJson = sharedPreferences.getString("characters", "[]")
        Log.d("SharedPreferencesHelper", "Characters loaded from SharedPreferences: $charactersJson")
        return Gson().fromJson(charactersJson, Array<CharacterModel>::class.java).toList()
    }

    fun saveCharacters(characters: List<CharacterModel>) {
        val charactersJson = Gson().toJson(characters)
        Log.d("SharedPreferencesHelper", "Saving characters to SharedPreferences: $charactersJson")
        sharedPreferences.edit().putString("characters", charactersJson).apply()
    }



}
