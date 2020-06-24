package com.india.acctest.data.network

import com.india.acctest.data.models.AnimalList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface NetworkCall {


    @GET
    @Headers("Content-Type:application/json")
    suspend fun getAnimals(@Url url:String): Response<AnimalList>

    companion object {
        operator fun invoke(): NetworkCall {


            val okkHttpclient = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://dl.dropboxusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkCall::class.java)
        }
    }
}