package com.lmy.hwahae.datasoruce.api

import com.lmy.hwahae.datasoruce.model.IndexViewDataModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface HwahaeWebService {

    /**
     * GET request to backend-api server
     */
    @GET("products")
    suspend fun getProductList(
        @Query("skin_type") skin_type: String,
        @Query("page") page: Int,
        @Query("search") search: String?
    ) : IndexViewDataModel

    /**
     * Create retrofit-service for backend-api server
     */
    companion object RetrofitBuilder {

        /* Server Base-Parameters */
        private const val BASE_URL = "https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/"
        private const val CONNECT_TIMEOUT = 15L
        private const val READ_TIMEOUT = 3L
        private const val WRITE_TIMEOUT = 3L

        private val client = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()

        val service: HwahaeWebService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HwahaeWebService::class.java)
    }
}