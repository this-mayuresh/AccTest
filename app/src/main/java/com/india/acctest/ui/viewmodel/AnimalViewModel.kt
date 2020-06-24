package com.india.acctest.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.india.acctest.data.repository.AnimalRepo

class AnimalViewModel(private val animalRepo: AnimalRepo):ViewModel() {

    val animalList by lazy {
        animalRepo.getAnimals()
    }


    suspend fun callAnimalList(){
        animalRepo.callAnimalList()
    }

    suspend fun clearTable(){
        animalRepo.clearData()
    }
}