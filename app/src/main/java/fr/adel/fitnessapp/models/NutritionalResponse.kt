package fr.adel.fitnessapp.models

data class NutritionalResponse(
    val foods: List<FoodItem>
)

data class FoodItem(
    val food_name: String,
    val brand_name: String?,
    val nf_calories: Float,
    val nf_protein: Float,
    val nf_carbohydrate: Float,
    val nf_fat: Float
)
