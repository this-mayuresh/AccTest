package com.india.acctest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.india.acctest.data.db.AppDatabase
import com.india.acctest.data.models.Animal
import com.india.acctest.data.network.NetworkCall
import java.lang.Exception

class AnimalRepo(private val networkCall: NetworkCall, private val db: AppDatabase) {


    suspend fun callAnimalList(){
        try{
            val result = networkCall.getAnimals("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json")

            if(result.isSuccessful){
                val list = result.body()
                list?.let {

                    db.getAnimalsDao().addAnimals(it.rows)
                }
            }
        }catch (e:Exception){
            Log.d("MAYUR",e.toString())
        }

    }

     fun getAnimals():LiveData<List<Animal>>{
        return db.getAnimalsDao().getAnimals()
    }

    suspend fun clearData(){
        db.getAnimalsDao().nukeAll()
    }
}