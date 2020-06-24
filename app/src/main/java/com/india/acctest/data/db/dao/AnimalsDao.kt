package com.india.acctest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.india.acctest.data.models.Animal

@Dao
interface AnimalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnimals(animal: List<Animal>)


    @Query("SELECT * from animal")
    fun getAnimals():LiveData<List<Animal>>


    @Query("DELETE FROM animal")
    suspend fun nukeAll()
}