package com.example.testtask.data.remote

import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.model.Spell
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<List<CharacterModel>>

    @GET("characters/house/{house}")
    suspend fun getCharactersByHouse(@Path("house") house: String): List<CharacterModel>

    @GET("spells")
    suspend fun getSpells(): Response<List<Spell>>
}
