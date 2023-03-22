package com.example.k1109_pyj_230321.retrofit

import com.example.k1109_pyj_230321.model.PageListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/api/food/img")
    fun getList(
        @Query("serviceKey") serviceKey: String?
    ): Call<PageListModel>

}