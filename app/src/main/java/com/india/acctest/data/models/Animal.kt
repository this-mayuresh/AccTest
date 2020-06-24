package com.india.acctest.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal")
data class Animal(

    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val description: String?,
    val imageHref: String?,
    val title: String?
)