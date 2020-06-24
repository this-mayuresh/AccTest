package com.india.acctest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.india.acctest.data.repository.AnimalRepo

class AnimalViewModelFactory(private val animalRepo: AnimalRepo): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnimalViewModel(animalRepo) as T
    }
}