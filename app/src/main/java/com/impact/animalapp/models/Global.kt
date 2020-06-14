package com.impact.animalapp.models

class Global {
    companion object {
        var typeList = mutableListOf<String>()
        var selectedType: String? = null
        var animalList = mutableListOf<Animal>()
        var animal: Animal? = null
        var shelter: Shelter? = null
        var user: User? = null
        var selectedShelter: String? = null
        var shelterList = mutableListOf<Shelter>()
    }
}