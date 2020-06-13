package com.impact.animalapp.models

data class User(
    val uid: String,
    val name: String,
    val organization: String,
    val isWorker: String,
    val email: String
)