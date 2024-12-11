package fr.adel.fitnessapp.models

data class NutritionalResponse(
    val foods: List<FoodItem>
)

data class FoodItem(
    val food_name: String,
    val nf_calories: Float,
    val nf_protein: Float,
    val nf_total_carbohydrate: Float,
    val photo: FoodPhoto // Modèle pour gérer l'image
)

data class FoodPhoto(
    val thumb: String // URL de l'image
)



