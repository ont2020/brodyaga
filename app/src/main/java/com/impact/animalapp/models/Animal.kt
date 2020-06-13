package com.impact.animalapp.models

data class Animal (
    val type: String,
    val date_require: String,
    val contacts: String,
    val shelter: String,
    var status: String,
    val image: String,
    val stateHealth: String,
    var isChip: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val animalDocId: String
)