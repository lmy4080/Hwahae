package com.lmy.hwahae.datasoruce.api

import com.lmy.hwahae.datasoruce.model.IndexViewModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HwahaeWebService {

    /**
     * GET request to backend-api server
     */
    @GET("products")
    suspend fun getProductList(
        @Query("skin_type") skin_type: String,
        @Query("page") page: Int,
        @Query("search") search: String?
    ) : IndexViewModel

}