package com.india.acctest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.india.acctest.data.db.dao.AnimalsDao
import com.india.acctest.data.models.Animal

@Database(entities = [Animal::class],version = 1,exportSchema = true)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getAnimalsDao(): AnimalsDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Acc.db"
            ).build()
    }
}