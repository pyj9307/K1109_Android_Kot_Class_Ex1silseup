package com.example.k1109_pyj_230321

import android.app.Application
import com.example.k1109_pyj_230321.retrofit.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    //add....................................
    var networkService: NetworkService

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl("https://busan-7beach.openapi.redtable.global")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    init {
        networkService = retrofit.create(NetworkService::class.java)
    }

}