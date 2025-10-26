package com.example.apirestapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("colors")
    suspend fun getColors(): List<Color>

}