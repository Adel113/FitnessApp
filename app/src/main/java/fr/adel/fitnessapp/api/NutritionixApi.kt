package fr.adel.fitnessapp.api

import fr.adel.fitnessapp.models.NutritionalResponse
import fr.adel.fitnessapp.models.QueryRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NutritionixApi {
    @POST("v2/natural/nutrients")
    fun getData(@Body queryRequest: QueryRequest): Call<NutritionalResponse>
}
