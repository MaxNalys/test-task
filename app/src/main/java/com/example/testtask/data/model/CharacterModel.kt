package com.example.testtask.data.model

import java.io.Serializable

data class CharacterModel(
    val id: String,
    val name: String,
    val alternate_names: List<String>,
    val species: String,
    val gender: String,
    val house: String?,
    val dateOfBirth: String?,
    val yearOfBirth: Int?,
    val wizard: Boolean,
    val ancestry: String?,
    val eyeColour: String?,
    val hairColour: String?,
    val patronus: String?,
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean,
    val actor: String?,
    val image: String?,
    var spells: MutableList<String> = mutableListOf()
) : Serializable
