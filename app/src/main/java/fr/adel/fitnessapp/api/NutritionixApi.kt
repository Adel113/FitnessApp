package fr.adel.fitnessapp.network

import fr.adel.fitnessapp.models.ExerciseResponse
import fr.adel.fitnessapp.models.NutritionalResponse
import fr.adel.fitnessapp.models.QueryRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NutritionixApi {

    @Headers(
        "x-app-id: aee95da4",
        "x-app-key: b060a0c64bc64d9a29f2e82c0f4cb8f2",
        "Content-Type: application/json"
    )
    @POST("v2/natural/nutrients")
    fun getData(@Body request: QueryRequest): Call<NutritionalResponse>

    @Headers(
        "x-app-id: aee95da4",
        "x-app-key: b060a0c64bc64d9a29f2e82c0f4cb8f2",
        "Content-Type: application/json"
    )
    @POST("v2/natural/exercise")
    fun getExerciseData(@Body request: QueryRequest): Call<ExerciseResponse>


}
