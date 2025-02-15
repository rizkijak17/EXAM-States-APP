package com.example.statesapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("?drilldowns=State&measures=Population&year=latest")
    fun getStateList(): Call<StatesResponse>

    companion object {
        private const val BASE_URL = "https://datausa.io/api/data/"

        fun create(): ApiService {
            // Membuat interceptor untuk logging
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // Mengatur timeout
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // Waktu tunggu untuk koneksi
                .readTimeout(60, TimeUnit.SECONDS)     // Waktu tunggu untuk membaca data
                .writeTimeout(60, TimeUnit.SECONDS)    // Waktu tunggu untuk menulis data
                .addInterceptor(loggingInterceptor)    // Menambahkan logging interceptor
                .build()

            // Membuat Retrofit instance dengan OkHttpClient
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)  // Menggunakan OkHttpClient yang sudah dikonfigurasi
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
