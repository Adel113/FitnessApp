package fr.adel.fitnessapp.network

import fr.adel.fitnessapp.api.NutritionixApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "https://trackapi.nutritionix.com/"

    val api: NutritionixApi

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("x-app-id", "aee95da4")  // Remplace par ton app id
                    .addHeader("x-app-key", "b060a0c64bc64d9a29f2e82c0f4cb8f2")  // Remplace par ta clé API
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(NutritionixApi::class.java)
    }
}
