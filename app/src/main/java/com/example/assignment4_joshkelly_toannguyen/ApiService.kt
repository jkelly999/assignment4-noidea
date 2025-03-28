package com.example.assignment4_joshkelly_toannguyen

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    fun getTodos(): Call<List<Todo>>
}